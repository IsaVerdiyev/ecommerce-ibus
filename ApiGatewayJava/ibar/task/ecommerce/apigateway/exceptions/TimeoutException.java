package ibar.task.ecommerce.apigateway.exceptions;

import ibar.task.ecommerce.apigateway.errors.HttpStatus;

@SuppressWarnings("serial")
public class TimeoutException extends CommonException {

    public TimeoutException(){
        super("TIMEOUT OCCURED", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}

