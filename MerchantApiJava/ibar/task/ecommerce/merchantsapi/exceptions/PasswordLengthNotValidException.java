package ibar.task.ecommerce.merchantsapi.exceptions;

import ibar.task.ecommerce.merchantsapi.errors.HttpStatus;

@SuppressWarnings("serial")
public class PasswordLengthNotValidException extends CommonException {

    public PasswordLengthNotValidException(){
        super( "PASSWORD LENGTH NOT VALID", HttpStatus.BAD_REQUEST, "password length is too small");
    }
}
