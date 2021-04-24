package ibar.task.ecommerce.authenticatorapi.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AuthenticationInfo {
    String merchantName;
    String password;
    Boolean isRemembered = false;

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
