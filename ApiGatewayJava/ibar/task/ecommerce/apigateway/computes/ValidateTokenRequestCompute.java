package ibar.task.ecommerce.apigateway.computes;

import ibar.task.ecommerce.apigateway.exceptions.NotSignedInException;
import ibar.task.ecommerce.apigateway.exceptions.TokenNotValidException;
import ibar.task.ecommerce.apigateway.models.GlobalEnvEnum;

import java.util.Base64;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.ibm.broker.plugin.MbElement;

public class ValidateTokenRequestCompute extends AbstractInputCompute {

	@Override
	protected void compute(BaseJavaComputeEvaluator evaluator, String body)
			throws Exception {
		Map<String, String> queryParams = evaluator.getQueryParamsMap();
		evaluator.globalEnv.set(
				GlobalEnvEnum.INPUT_QUERY_PARAMS.name(),
				evaluator.context.getObjectMapper().writeValueAsString(
						queryParams));
		String token = getStrOrEmptyStrFromMbElement(evaluator.localEnv
				.getMbElement().getFirstElementByPath(
						"/REST/Input/Parameters/token"));
		if (StringUtils.isBlank(token)) {
			throw new NotSignedInException();
		}
		evaluator.globalEnv.set(GlobalEnvEnum.TOKEN.name(), token);

		String merchantName = getMerchantNameFromToken(token);
		evaluator.globalEnv.set(GlobalEnvEnum.MERCHANT_NAME.name(),
				merchantName);
		String url = evaluator.context.getConfigurableService().get(
				"AuthenticatorUrl")
				+ "/validateToken";
		evaluator.localEnv.set("Destination/HTTP/RequestURL", url);
		evaluator.localEnv.set("Destination/HTTP/RequestLine/Method", "POST");
		MbElement headerElement = evaluator.getOutputRootMbElement()
				.createElementAsFirstChild("HTTPInputHeader");
		headerElement.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,
				"token", token);
		String requestLog = evaluator.generateRequestLog();
		logger.info(evaluator.getMessageId(), "requestLog: \n" + requestLog);
	}

	public String getMerchantNameFromToken(String token) throws TokenNotValidException {
		try {
			String[] tokenParts = token.split("\\.");
			String bodyChunk = tokenParts[1];
			byte[] decodedBytes = Base64.getDecoder().decode(bodyChunk);
			String body = new String(decodedBytes);
			JSONObject json = new JSONObject(body);
			return json.get("sub").toString();
		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			throw new TokenNotValidException(e);
		}

	}
}