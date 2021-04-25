package ibar.task.ecommerce.merchantsapi.computes;

import ibar.task.ecommerce.merchantsapi.dao.MerchantDao;
import ibar.task.ecommerce.merchantsapi.exceptions.MerchantAlreadyExists;
import ibar.task.ecommerce.merchantsapi.models.Merchant;

import java.sql.SQLException;

public class AddMerchantCompute extends AbstractInputCompute<Merchant> {

	@Override
	protected void compute(BaseJavaComputeEvaluator evaluator, String body)
			throws Exception {
		Merchant merchant = evaluator.context.getJsonDeserializer()
				.deserializeToObjectIfValid(body, Merchant.class);

		evaluator.context.getPasswordValidator().validateSignUpPassword(
				merchant);
		if (!doesMerchantAlreadyExists(evaluator.context.getMerchantDao(),
				merchant)) {
			merchant = evaluator.context.getMerchantDao().addMerchant(merchant);
		} else {
			throw new MerchantAlreadyExists();
		}

		evaluator.setJsonOutput(evaluator.context.getObjectMapper()
				.writeValueAsString(merchant));
	}

	private boolean doesMerchantAlreadyExists(MerchantDao merchantDao,
			Merchant merchant) throws SQLException {
		return merchantDao.findByName(merchant.getName()) != null;
	}

}
