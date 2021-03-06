package ibar.task.ecommerce.authenticatorapi.exceptions;

import ibar.task.ecommerce.authenticatorapi.errors.HttpStatus;

@SuppressWarnings("serial")
public class InvalidContentException extends CommonException {

    public InvalidContentException(String message, Throwable e){
        super("INVALID CONTENT", HttpStatus.BAD_REQUEST, message, e);
    }
    
    public InvalidContentException(String message){
        super("INVALID CONTENT", HttpStatus.BAD_REQUEST, message);
    }
}
