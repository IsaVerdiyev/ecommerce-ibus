package ibar.task.ecommerce.authenticatorapi.exceptions;

import ibar.task.ecommerce.authenticatorapi.errors.HttpStatus;

@SuppressWarnings("serial")
public class UknownException  extends CommonException {

    public UknownException(){
        super("SERVER ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}

