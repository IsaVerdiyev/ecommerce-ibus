package ibar.task.ecommerce.apigateway.computes;

import ibar.task.ecommerce.apigateway.models.GlobalEnvEnum;


public class SignInInputCompute extends AbstractInputCompute {

	@Override
	protected void compute(BaseJavaComputeEvaluator evaluator, String body)
			throws Exception {
		evaluator.setJsonOutput(body);
		evaluator.globalEnv.set(GlobalEnvEnum.INPUT_REQUEST.name(), body);
		String url = evaluator.context.getConfigurableService().get(
				"MerchantsApiUrl") + "/signIn";
		evaluator.localEnv.set("Destination/HTTP/RequestURL", url);
		evaluator.localEnv.set("Destination/HTTP/RequestLine/Method", "POST");
		String requestLog = evaluator.generateRequestLog();
		logger.info(evaluator.getMessageId(), "requestLog: \n" + requestLog);
	}

}