package ibar.task.ecommerce.apigateway.computes;

import ibar.task.ecommerce.apigateway.models.Merchant;

public class SingUpInputCompute extends AbstractInputCompute<Merchant> {

	@Override
	protected void compute(BaseJavaComputeEvaluator evaluator, String body)
			throws Exception {
		evaluator.setJsonOutput(body);
		String url = evaluator.context.getConfigurableService().get(
				"MerchantsApiUrl");
		evaluator.localEnv.set("Destination/HTTP/RequestURL", url);
		evaluator.localEnv.set("Destination/HTTP/RequestLine/Method", "POST");
		String requestLog = evaluator.generateRequestLog();
		logger.info(evaluator.getMessageId(), "requestLog: \n" + requestLog);
	}

}