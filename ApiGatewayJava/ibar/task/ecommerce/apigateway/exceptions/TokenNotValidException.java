package ibar.task.ecommerce.apigateway.exceptions;

import ibar.task.ecommerce.apigateway.errors.HttpStatus;

@SuppressWarnings("serial")
public class TokenNotValidException extends CommonException {

    public TokenNotValidException(Throwable e){
        super("TOKEN NOT VALID", HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }
    
    public TokenNotValidException(){
        super("TOKEN NOT VALID", HttpStatus.BAD_REQUEST, "token is not valid");
    }
}
