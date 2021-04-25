package ibar.task.ecommerce.apigateway.computes;

import ibar.task.ecommerce.apigateway.models.GlobalEnvEnum;

public class GetMerchantRequestCompute extends BaseCompute {

	@Override
	protected JavaComputeEvaluator getEvaluator() {
		return new BaseJavaComputeEvaluator() {

			@Override
			public boolean evaluate() throws Exception {
				try {
					init();
					String merchantName = globalEnv
							.getAsString(GlobalEnvEnum.MERCHANT_NAME.name());
					String url = context.getConfigurableService().get(
							"MerchantsApiUrl") + "?name=" + merchantName;
					localEnv.set("Destination/HTTP/RequestURL", url);
					localEnv.set("Destination/HTTP/RequestLine/Method", "GET");
					

	

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