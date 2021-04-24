package ibar.task.ecommerce.authenticatorapi.exceptions;

import ibar.task.ecommerce.authenticatorapi.errors.HttpStatus;

@SuppressWarnings("serial")
public class TokenNotSentException extends CommonException {

    public TokenNotSentException(Throwable e){
        super("TOKEN NOT SENT", HttpStatus.BAD_REQUEST, "token was not sent in header or was empty", e);
    }
    
    public TokenNotSentException(){
        super("TOKEN NOT SENT", HttpStatus.BAD_REQUEST, "token was not sent in header or was empty");
    }
}