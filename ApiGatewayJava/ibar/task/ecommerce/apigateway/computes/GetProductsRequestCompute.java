package ibar.task.ecommerce.apigateway.computes;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ibm.broker.plugin.MbElement;

import ibar.task.ecommerce.apigateway.models.GlobalEnvEnum;
import ibar.task.ecommerce.apigateway.models.Merchant;

public class GetProductsRequestCompute extends BaseCompute {

	@Override
	protected JavaComputeEvaluator getEvaluator() {
		return new BaseJavaComputeEvaluator() {

			@Override
			public boolean evaluate() throws Exception {
				try {
					init();
				
					String body = new String(getInputRootElement()
							.getFirstElementByPath("BLOB")
							.toBitstream("", "", "", 1208, 1208, 0));
					
					Merchant merchant = context.getObjectMapper().readValue(body, Merchant.class);
					
					String url = context.getConfigurableService().get(
							"ProductsApiUrl");
					localEnv.set("Destination/HTTP/RequestURL", url);
					localEnv.set("Destination/HTTP/RequestLine/Method", "GET");
					Map<String, String> queryParams = context
							.getObjectMapper()
							.readValue(
									globalEnv.getAsString(GlobalEnvEnum.INPUT_QUERY_PARAMS
											.name()),
									new TypeReference<Map<String, String>>() {
									});
					queryParams.put("merchant_id", merchant.getId().toString());
					queryParams.put("inventorySize_gth", String.valueOf(5));
					MbElement headerElement = getOutputRootElement()
							.createElementAsFirstChild("HTTPInputHeader");
					for (Map.Entry<String, String> keyValue : queryParams
							.entrySet()) {
						headerElement.createElementAsLastChild(
								MbElement.TYPE_NAME_VALUE, keyValue.getKey(),
								keyValue.getValue());
					}
					String requestLog = generateRequestLog();
					logger.info(getMessageId(), "requestLog: \n" + requestLog);
					propagate();
				} catch (Exception e) {
					handleException(this, e);
				}
				return false;
			}
		};
	}
}