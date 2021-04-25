package ibar.task.ecommerce.products.models;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DeliveryOptions {
	@NotBlank
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
