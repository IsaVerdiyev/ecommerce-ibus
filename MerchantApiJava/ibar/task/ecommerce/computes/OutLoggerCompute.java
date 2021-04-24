package ibar.task.ecommerce.computes;

import com.ibm.broker.plugin.MbElement;

public class OutLoggerCompute extends BaseCompute{

	@Override
	protected JavaComputeEvaluator getEvaluator() {
		return new BaseJavaComputeEvaluator() {

			@Override
			public boolean evaluate() throws Exception {

				try {
					init();

					outputRoot().set(inputRoot());

					String outJson = new String(outputRoot().getMbElement().getFirstElementByPath("BLOB")
							.toBitstream("", "", "", 1208, 1208, 0));

					String statusCode = null;

					MbElement mbelement = inputRoot().getMbElement().getFirstElementByPath(
							"HTTPReplyHeader/X-Original-HTTP-Status-Code");

					if (mbelement != null) {
						statusCode = mbelement.getValueAsString();
					} else {
						statusCode = "200";
					}

					String result = "";

					result += "status code : " + statusCode + "\n\ncontent : \n" + outJson;
					context.getLogger().info(messageId, "outResponse: \n" + result);
					propagate();
				} catch (Exception e) {
					handleException(this, e);
				}

				return false;
			}
		};
	}
	
}