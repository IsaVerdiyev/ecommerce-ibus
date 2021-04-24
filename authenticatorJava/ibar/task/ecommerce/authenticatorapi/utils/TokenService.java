package ibar.task.ecommerce.authenticatorapi.utils;

import ibar.task.ecommerce.authenticatorapi.models.AuthenticationInfo;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class TokenService {
	String secretKey;
	
	String issuer;
	
	Integer expirationHours;
	
	Integer expirationHoursForRemembered;
	
	
	public TokenService(String secretKey, String issuer,
			Integer expirationHours, Integer expirationHoursForRemembered) {
		super();
		this.secretKey = secretKey;
		this.issuer = issuer;
		this.expirationHours = expirationHours;
		this.expirationHoursForRemembered = expirationHoursForRemembered;
	}

	public void validateToken(String token) {
       Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token).getBody();
    }

    public String generateToken(AuthenticationInfo authenticationInfo) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date issueDate = new Date();
        Date expirationDate = getExpirationDate(issueDate, authenticationInfo.getRemembered());
        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());


        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(issueDate)
                .setSubject(authenticationInfo.getMerchantName())
                .setIssuer(issuer)
                .setExpiration(expirationDate)
                .signWith(signatureAlgorithm, signingKey);
        String token = builder.compact();

        return token;
    }

    Date getExpirationDate(Date issueDate, Boolean isRemembered) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(issueDate);
        if (!isRemembered) {
            cal.add(Calendar.HOUR, expirationHours);
        } else {
            cal.add(Calendar.HOUR, expirationHoursForRemembered);
        }
        return cal.getTime();
    }
	
}
