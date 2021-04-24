package ibar.task.ecommerce.merchantsapi.exceptions;

import ibar.task.ecommerce.merchantsapi.errors.HttpStatus;

@SuppressWarnings("serial")
public class UknownException  extends CommonException {

    public UknownException(){
        super("SERVER ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}

