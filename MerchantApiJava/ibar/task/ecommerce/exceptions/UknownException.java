package ibar.task.ecommerce.exceptions;

import ibar.task.ecommerce.errors.HttpStatus;

@SuppressWarnings("serial")
public class UknownException  extends CommonException {

    public UknownException(){
        super("SERVER ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}

