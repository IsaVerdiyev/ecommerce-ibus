package ibar.task.ecommerce.products.computes;

import ibar.task.ecommerce.products.exceptions.NotParseableJsonException;

import com.ibm.broker.plugin.MbParserException;

public abstract class AbstractInputCompute<T> extends BaseCompute {
	@Override
	protected JavaComputeEvaluator getEvaluator() {

		return new BaseJavaComputeEvaluator() {

			@Override
			public boolean evaluate() throws Exception {
				try {

					String inputMessage = null;
					try {
						init();
						inputMessage = new String(getInputRootElement().toBitstream("", "", "", 1208, 1208, 0));
						String body = null;
						logger.info(getMessageId(), "input message: " + inputMessage);
						try {
							if (getInputRootElement().getFirstElementByPath("/JSON/Data") != null) {

								body = new String(getInputRootElement().getFirstElementByPath("/JSON/Data").toBitstream("", "",
										"", 1208, 1208, 0));
							}
						} catch (MbParserException e) {
							throw new NotParseableJsonException(e);
						} 
						compute(this, body);
					} finally {
						/*setLogsFormDataFromGlobalEnv(this);
						dao.insertLog(logsForm, "IN", inputMessage);*/
					}
					propagate();
				} catch (Exception e) {
					handleException(this, e);
				}
				return false;
			}
		};
	}
	
	protected abstract void compute(BaseJavaComputeEvaluator evaluator, String body) throws Exception;
}