package ibar.task.ecommerce.exceptions;

import ibar.task.ecommerce.errors.HttpStatus;

@SuppressWarnings("serial")
public class TimeoutException extends CommonException {

    public TimeoutException(){
        super("TIMEOUT OCCURED", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}

