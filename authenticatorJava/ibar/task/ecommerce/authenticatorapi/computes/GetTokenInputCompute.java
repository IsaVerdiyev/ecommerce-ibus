package ibar.task.ecommerce.authenticatorapi.computes;

import ibar.task.ecommerce.authenticatorapi.models.AuthenticationInfo;

public class GetTokenInputCompute extends AbstractInputCompute {

	@Override
	protected void compute(BaseJavaComputeEvaluator evaluator, String body)
			throws Exception {
		AuthenticationInfo authenticationInfo = evaluator.context
				.getJsonDeserializer()
				.deserializeToObjectIfValid(body, AuthenticationInfo.class);
		
		String token = evaluator.context.getTokenService().generateToken(authenticationInfo);
		evaluator.outputRoot.set("HTTPReplyHeader/token", token);
	}

}
