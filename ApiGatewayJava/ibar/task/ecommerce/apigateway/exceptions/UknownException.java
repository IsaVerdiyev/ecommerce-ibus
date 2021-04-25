package ibar.task.ecommerce.apigateway.exceptions;

import ibar.task.ecommerce.apigateway.errors.HttpStatus;

@SuppressWarnings("serial")
public class UknownException  extends CommonException {

    public UknownException(){
        super("SERVER ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}

