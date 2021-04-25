package ibar.task.ecommerce.apigateway.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DeliveryOptions {

    String someDeliveryOption;

	public String getSomeDeliveryOption() {
		return someDeliveryOption;
	}

	public void setSomeDeliveryOption(String someDeliveryOption) {
		this.someDeliveryOption = someDeliveryOption;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
