package ibar.task.ecommerce.products.errors;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public
class ApiError {
    @JsonIgnore
    private Integer statusCode;
    
    @JsonProperty("status")
    private String statusDescription;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    @JsonProperty("error")
    private String error;

    @JsonProperty("message")
    private String message;
    
    public ApiError() {
        timestamp = new Date();
    }

    public ApiError(Integer statusCode, String statusDescription, String error, String message) {
        this.timestamp = new Date();
        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
        this.error = error;
        this.message = message;
    }

    public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

