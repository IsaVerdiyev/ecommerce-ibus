package ibar.task.ecommerce.exceptions;

import ibar.task.ecommerce.errors.ApiError;
import ibar.task.ecommerce.errors.HttpStatus;

@SuppressWarnings("serial")
public class CommonException extends Exception{
    String error;
    HttpStatus httpStatusCode;

    public CommonException(String error, HttpStatus statusCode) {
        this.httpStatusCode = statusCode;
        this.error = error;
    }

    public CommonException(String error, HttpStatus statusCode, String message,Throwable cause) {
        super(message, cause);
        this.httpStatusCode = statusCode;
        this.error = error;
    }

    public CommonException(String error, HttpStatus statusCode, String message) {
        super(message);
        this.httpStatusCode = statusCode;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public HttpStatus getHttpStatusCode() {
		return httpStatusCode;
	}

	public ApiError getApiError(){
        return new ApiError(httpStatusCode.getCode(), httpStatusCode.getDescription(), error, getMessage());
    }
}
