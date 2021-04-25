package ibar.task.ecommerce.apigateway.computes;

import ibar.task.ecommerce.apigateway.exceptions.NotParseableJsonException;
import ibar.task.ecommerce.apigateway.models.Merchant;

import com.ibm.broker.plugin.MbParserException;

public class SignUpOutputCompute extends BaseCompute {

	@Override
	protected JavaComputeEvaluator getEvaluator() {
		return new BaseJavaComputeEvaluator() {

			@Override
			public boolean evaluate() throws Exception {
				try {
					init();

					String body = null;
					try {
						if (getInputRootElement().getFirstElementByPath(
								"BLOB") != null) {

							body = new String(getInputRootElement()
									.getFirstElementByPath("BLOB")
									.toBitstream("", "", "", 1208, 1208, 0));
						}
					} catch (MbParserException e) {
						throw new NotParseableJsonException(e);
					}
					context.getJsonDeserializer().deserializeToObjectIfValid(
							body, Merchant.class);
					setJsonOutput("");
					propagate();
				} catch (Exception e) {
					handleException(this, e);
				}
				return false;
			}
		};
	}

}