package ibar.task.ecommerce.merchantsapi.computes;

import org.apache.commons.lang3.StringUtils;

import ibar.task.ecommerce.merchantsapi.exceptions.MerchantNameNotSentException;
import ibar.task.ecommerce.merchantsapi.exceptions.MerchantNotFoundException;
import ibar.task.ecommerce.merchantsapi.models.Merchant;

public class GetMerchantByName extends AbstractInputCompute {

	@Override
	protected void compute(BaseJavaComputeEvaluator evaluator, String body)
			throws Exception {
		String merchantName = getStrOrEmptyStrFromMbElement(evaluator.localEnv
				.getMbElement().getFirstElementByPath(
						"/REST/Input/Parameters/name"));
		if (StringUtils.isBlank(merchantName)) {
			throw new MerchantNameNotSentException();
		}
		Merchant merchant = evaluator.context.getMerchantDao().findByName(
				merchantName);
		if (merchant == null) {
			throw new MerchantNotFoundException();
		}
		evaluator.setJsonOutput(evaluator.context.getObjectMapper()
				.writeValueAsString(merchant));
	}

}