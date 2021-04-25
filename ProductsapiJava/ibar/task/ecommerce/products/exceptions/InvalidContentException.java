package ibar.task.ecommerce.products.exceptions;

import ibar.task.ecommerce.products.errors.HttpStatus;

@SuppressWarnings("serial")
public class InvalidContentException extends CommonException {

    public InvalidContentException(String message, Throwable e){
        super("INVALID CONTENT", HttpStatus.BAD_REQUEST, message, e);
    }
    
    public InvalidContentException(String message){
        super("INVALID CONTENT", HttpStatus.BAD_REQUEST, message);
    }
}
