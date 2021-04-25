package ibar.task.ecommerce.merchantsapi.computes;

import ibar.task.ecommerce.merchantsapi.exceptions.InvalidPasswordException;
import ibar.task.ecommerce.merchantsapi.exceptions.MerchantNotFoundException;
import ibar.task.ecommerce.merchantsapi.models.AuthenticationInfo;
import ibar.task.ecommerce.merchantsapi.models.Merchant;

public class SignInInputCompute extends AbstractInputCompute {

	@Override
	protected void compute(BaseJavaComputeEvaluator evaluator, String body)
			throws Exception {
		AuthenticationInfo authenticationInfo = evaluator.context
				.getJsonDeserializer().deserializeToObjectIfValid(body,
						AuthenticationInfo.class);

		Merchant merchant = evaluator.context.getMerchantDao().findByName(
				authenticationInfo.getMerchantName());
		if (merchant == null) {
			throw new MerchantNotFoundException();
		}
		if (!merchant.validatePassword(authenticationInfo.getPassword())) {
			throw new InvalidPasswordException();
		}

	}

}
