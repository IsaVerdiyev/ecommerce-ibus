package ibar.task.ecommerce.merchantsapi.exceptions;

import ibar.task.ecommerce.merchantsapi.errors.HttpStatus;

@SuppressWarnings("serial")
public class PasswordContentException extends CommonException {

    public PasswordContentException(){
        super( "PASSWORD CONTENT IS NOT VALID", HttpStatus.BAD_REQUEST, "password content is not valid");
    }
}
