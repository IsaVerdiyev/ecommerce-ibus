package ibar.task.ecommerce.apigateway.exceptions;

import ibar.task.ecommerce.apigateway.errors.HttpStatus;

@SuppressWarnings("serial")
public class NotSignedInException extends CommonException {

    public NotSignedInException(){
        super( "NOT SIGNED IN", HttpStatus.BAD_REQUEST, "can't send request without signing in");
    }
    public NotSignedInException(Throwable e){
        super( "NOT SIGNED IN", HttpStatus.BAD_REQUEST, "can't send request without signing in", e);
    }
}
