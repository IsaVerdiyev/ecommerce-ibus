package ibar.task.ecommerce.products.exceptions;

import ibar.task.ecommerce.products.errors.HttpStatus;

@SuppressWarnings("serial")
public class PageSizeNotNumberException extends CommonException {

    public PageSizeNotNumberException(){
        super( "NOT VALID PAGESIZE", HttpStatus.BAD_REQUEST, "coul't parse pageSize parameter into integer");
    }
    public PageSizeNotNumberException(Throwable e){
        super( "NOT VALID PAGESIZE", HttpStatus.BAD_REQUEST, "coul't parse pageSize into integer", e);
    }
}
