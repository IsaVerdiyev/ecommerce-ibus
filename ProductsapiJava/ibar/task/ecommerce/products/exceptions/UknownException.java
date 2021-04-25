package ibar.task.ecommerce.products.exceptions;

import ibar.task.ecommerce.products.errors.HttpStatus;

@SuppressWarnings("serial")
public class UknownException  extends CommonException {

    public UknownException(){
        super("SERVER ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}

