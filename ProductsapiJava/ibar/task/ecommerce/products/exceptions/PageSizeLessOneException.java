package ibar.task.ecommerce.products.exceptions;

import ibar.task.ecommerce.products.errors.HttpStatus;

@SuppressWarnings("serial")
public class PageSizeLessOneException extends CommonException {

    public PageSizeLessOneException(){
        super( "PAGE SIZE NOT VALID", HttpStatus.BAD_REQUEST, "page size can't be less than 1");
    }
    public PageSizeLessOneException(Throwable e){
        super( "PAGE SIZE NOT VALID", HttpStatus.BAD_REQUEST, "page size can't be less than 1", e);
    }
}
