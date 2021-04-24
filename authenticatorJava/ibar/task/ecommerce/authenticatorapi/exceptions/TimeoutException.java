package ibar.task.ecommerce.authenticatorapi.exceptions;

import ibar.task.ecommerce.authenticatorapi.errors.HttpStatus;

@SuppressWarnings("serial")
public class TimeoutException extends CommonException {

    public TimeoutException(){
        super("TIMEOUT OCCURED", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}

