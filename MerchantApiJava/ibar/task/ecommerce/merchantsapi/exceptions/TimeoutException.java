package ibar.task.ecommerce.merchantsapi.exceptions;

import ibar.task.ecommerce.merchantsapi.errors.HttpStatus;

@SuppressWarnings("serial")
public class TimeoutException extends CommonException {

    public TimeoutException(){
        super("TIMEOUT OCCURED", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}

