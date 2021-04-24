package ibar.task.ecommerce.merchantsapi.exceptions;

import ibar.task.ecommerce.merchantsapi.errors.HttpStatus;

@SuppressWarnings({ "serial"})
public class InvalidPasswordException extends CommonException {

    public InvalidPasswordException(){
        super("INVALID PASSWORD", HttpStatus.BAD_REQUEST);
    }
}
