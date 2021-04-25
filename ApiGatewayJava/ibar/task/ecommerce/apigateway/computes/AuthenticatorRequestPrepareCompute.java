package ibar.task.ecommerce.apigateway.computes;

import ibar.task.ecommerce.apigateway.models.GlobalEnvEnum;

public class AuthenticatorRequestPrepareCompute extends BaseCompute {

	@Override
	protected JavaComputeEvaluator getEvaluator() {
		return new BaseJavaComputeEvaluator() {

			@Override
			public boolean evaluate() throws Exception {
				try {
					init();
					setJsonOutput(globalEnv
							.getAsString(GlobalEnvEnum.INPUT_REQUEST.name()));
					String url = context.getConfigurableService().get(
							"AuthenticatorUrl")
							+ "/getToken";
					localEnv.set("Destination/HTTP/RequestURL", url);
					localEnv.set("Destination/HTTP/RequestLine/Method", "POST");
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