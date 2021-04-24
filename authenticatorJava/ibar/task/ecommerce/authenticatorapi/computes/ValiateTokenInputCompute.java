package ibar.task.ecommerce.authenticatorapi.computes;

import ibar.task.ecommerce.authenticatorapi.exceptions.TokenNotSentException;
import ibar.task.ecommerce.authenticatorapi.exceptions.TokenNotValidException;
import io.jsonwebtoken.MalformedJwtException;

import org.apache.commons.lang3.StringUtils;



public class ValiateTokenInputCompute extends AbstractInputCompute<String>{

	@Override
	protected void compute(BaseJavaComputeEvaluator evaluator, String body)
			throws Exception {
		String token = getStrOrEmptyStrFromMbElement(evaluator.localEnv.getMbElement()
				.getFirstElementByPath("/REST/Input/Parameters/token"));
		if(StringUtils.isBlank(token)){
			throw new TokenNotSentException();
		}
		try{
		evaluator.context.getTokenService().validateToken(token);
		}catch(MalformedJwtException e){
			throw new TokenNotValidException(e);
		}
		evaluator.outputRoot.set("HTTPReplyHeader/token", token);
	}
	
}