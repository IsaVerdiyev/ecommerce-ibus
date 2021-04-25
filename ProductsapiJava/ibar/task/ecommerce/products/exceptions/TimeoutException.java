package ibar.task.ecommerce.products.exceptions;

import ibar.task.ecommerce.products.errors.HttpStatus;

@SuppressWarnings("serial")
public class TimeoutException extends CommonException {

    public TimeoutException(){
        super("TIMEOUT OCCURED", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}

