package ibar.task.ecommerce.exceptions;

import ibar.task.ecommerce.errors.HttpStatus;

@SuppressWarnings("serial")
public class PasswordContentException extends CommonException {

    public PasswordContentException(){
        super( "PASSWORD CONTENT IS NOT VALID", HttpStatus.BAD_REQUEST, "password content is not valid");
    }
}
