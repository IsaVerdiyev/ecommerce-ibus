package ibar.task.ecommerce.exceptions;

import ibar.task.ecommerce.errors.HttpStatus;

@SuppressWarnings("serial")
public class MerchantAlreadyExists extends CommonException {

    public MerchantAlreadyExists(){
        super("MERCHANT EXISTS", HttpStatus.BAD_REQUEST, "merchant with this name already exists");
    }
}
