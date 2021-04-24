package ibar.task.ecommerce.computes;

import ibar.task.ecommerce.dao.MerchantDao;
import ibar.task.ecommerce.exceptions.InvalidContentException;
import ibar.task.ecommerce.exceptions.MerchantAlreadyExists;
import ibar.task.ecommerce.models.Merchant;
import ibar.task.ecommerce.utils.NullSettingDesirializationProblemHandler;

import java.sql.SQLException;
import java.util.Set;

import javax.validation.ConstraintViolation;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

public class AddMerchantCompute extends AbstractInputCompute<Merchant> {

	@Override
	protected void compute(BaseJavaComputeEvaluator evaluator, String body)
			throws Exception {
		Merchant merchant;
		try {
			merchant = evaluator.context.getObjectMapper().readValue(body,
					Merchant.class);
		} catch (UnrecognizedPropertyException e) {
			throw new InvalidContentException(e.getPropertyName()
					+ " is not recognized", e);
		} catch (InvalidFormatException e) {
			throw new InvalidContentException("Invalid format of "
					+ e.getPath().get(e.getPath().size() - 1).getFieldName()
					+ ". Invalid value " + e.getValue(), e);
		} finally {
			evaluator.context.getObjectMapper().configure(
					DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			evaluator.context.getObjectMapper().addHandler(
					new NullSettingDesirializationProblemHandler());
			// setGlobalEnvVarialbles(evaluator,
			// evaluator.mapper.readValue(body,
			// GetInvoicesByServiceCompanyRequest.class));
		}
		Set<ConstraintViolation<Merchant>> violations = evaluator.context
				.getValidator().validate(merchant);
		for (ConstraintViolation<Merchant> violation : violations) {
			throw new InvalidContentException(violation.getPropertyPath() + " "
					+ violation.getMessage() + ". Invalid value "
					+ violation.getInvalidValue());
		}
		evaluator.context.getPasswordValidator().validateSignUpPassword(merchant);
        if(!doesMerchantAlreadyExists(evaluator.context.getMerchantDao(), merchant)){
        	merchant = evaluator.context.getMerchantDao().addMerchant(merchant);
        }
        else{
            throw new MerchantAlreadyExists();
        }
		
		evaluator.setJsonOutput(evaluator.context.getObjectMapper()
				.writeValueAsString(merchant));
	}
	
	private boolean doesMerchantAlreadyExists(MerchantDao merchantDao, Merchant merchant) throws SQLException{
		return merchantDao.findByName(merchant.getName()) != null;
	}

}
