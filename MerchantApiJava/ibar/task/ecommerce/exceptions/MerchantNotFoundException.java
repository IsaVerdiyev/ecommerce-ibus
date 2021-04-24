package ibar.task.ecommerce.exceptions;

import ibar.task.ecommerce.errors.HttpStatus;

@SuppressWarnings("serial")
public class MerchantNotFoundException extends CommonException {

    public MerchantNotFoundException(){
        super("MERCHANT NOT FOUND", HttpStatus.NOT_FOUND, "merchant with this name doesn't exist");
    }
}
