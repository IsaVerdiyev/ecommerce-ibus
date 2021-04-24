package ibar.task.ecommerce.exceptions;

import ibar.task.ecommerce.errors.HttpStatus;

@SuppressWarnings({ "serial"})
public class InvalidPasswordException extends CommonException {

    public InvalidPasswordException(){
        super("INVALID PASSWORD", HttpStatus.BAD_REQUEST);
    }
}
