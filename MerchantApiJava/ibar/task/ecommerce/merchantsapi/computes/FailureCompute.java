package ibar.task.ecommerce.merchantsapi.computes;

import ibar.task.ecommerce.merchantsapi.errors.ApiError;
import ibar.task.ecommerce.merchantsapi.exceptions.TimeoutException;
import ibar.task.ecommerce.merchantsapi.exceptions.UknownException;

import org.w3c.dom.Document;

import com.ibm.broker.plugin.MbElement;

public class FailureCompute extends BaseCompute {

	@Override
	protected JavaComputeEvaluator getEvaluator() {
		return new BaseJavaComputeEvaluator() {

			@Override
			public boolean evaluate() throws Exception {
				try {
					init();
					Document doc = getInAssembly().getExceptionList().getDOMDocument();
					String exceptionString = getXmlFromDoc(doc);
					context.getLogger().error(messageId, "exception string " + exceptionString);
					ApiError localError;
					if (exceptionString.toLowerCase().contains("sockettimeoutexception")) {
						localError = new TimeoutException().getApiError();
					} else {
						localError = new UknownException().getApiError();
					}

					setJsonOutput(context.getObjectMapper().writeValueAsString(localError));
					MbElement headerElement = getOutputRootElement().createElementAsFirstChild("HTTPReplyHeader");
					headerElement.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "X-Original-HTTP-Status-Code",
							localError.getStatusCode());
					propagate();
				} catch (Exception e) {
					handleException(this, e);
				}
				return false;
			}
		};
	}

}
