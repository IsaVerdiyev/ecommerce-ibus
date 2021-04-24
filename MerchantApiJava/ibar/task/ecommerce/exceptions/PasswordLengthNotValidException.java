package ibar.task.ecommerce.exceptions;

import ibar.task.ecommerce.errors.HttpStatus;

@SuppressWarnings("serial")
public class PasswordLengthNotValidException extends CommonException {

    public PasswordLengthNotValidException(){
        super( "PASSWORD LENGTH NOT VALID", HttpStatus.BAD_REQUEST, "password length is too small");
    }
}
