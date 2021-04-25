package ibar.task.ecommerce.merchantsapi.exceptions;

import ibar.task.ecommerce.merchantsapi.errors.HttpStatus;

@SuppressWarnings("serial")
public class MerchantNameNotSentException extends CommonException {

	public MerchantNameNotSentException() {
		super("MERCHANT NAME NOT SENT", HttpStatus.BAD_REQUEST,
				"merchant name was not sent in query");
	}
}
