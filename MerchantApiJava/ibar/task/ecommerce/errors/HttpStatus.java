package ibar.task.ecommerce.errors;

public enum HttpStatus {
	
	NOT_FOUND("NOT FOUND", 404), //
	INTERNAL_SERVER_ERROR("INTERNAL SERVER ERROR", 500),//
	BAD_REQUEST("BAD REQUEST", 400);
	
	private String description;
	private Integer code;

	private HttpStatus(String description, Integer code) {
		this.description = description;
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public Integer getCode() {
		return code;
	}
}
