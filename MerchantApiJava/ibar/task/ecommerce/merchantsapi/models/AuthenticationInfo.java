package ibar.task.ecommerce.merchantsapi.models;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AuthenticationInfo {
	@NotBlank
    String merchantName;
	
	@NotBlank
    String password;
	
    Boolean isRemembered;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRemembered() {
        return isRemembered;
    }

    public void setRemembered(Boolean remembered) {
        isRemembered = remembered;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
