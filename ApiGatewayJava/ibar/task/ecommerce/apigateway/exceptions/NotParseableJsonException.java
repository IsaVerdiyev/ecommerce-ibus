package ibar.task.ecommerce.apigateway.exceptions;

import ibar.task.ecommerce.apigateway.errors.HttpStatus;

@SuppressWarnings("serial")
public class NotParseableJsonException extends CommonException {

    public NotParseableJsonException(){
        super( "NOT VALID JSON", HttpStatus.BAD_REQUEST, "coul't parse body to json");
    }
    public NotParseableJsonException(Throwable e){
        super( "NOT VALID JSON", HttpStatus.BAD_REQUEST, "coul't parse body to json", e);
    }
}
