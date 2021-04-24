package ibar.task.ecommerce.authenticatorapi.utils;

import java.io.IOException;

import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import ua.com.integrity.broker.BrokerException;
import ua.com.integrity.broker.ConfigurableService;
import ua.com.integrity.broker.ConfigurableServiceException;
import ua.com.integrity.logging.ClassicLogger;

import com.fasterxml.jackson.databind.ObjectMapper;


public class ApplicationContext {
	private ObjectMapper mapper;
	private ConfigurableService cs;
	private StackTraceReceiver stackTraceReciever;
	private Validator validator;
	private ClassicLogger logger;
	private String messageId;
	private JsonDeserializer jsonDeserializer;
	private TokenService tokenService;
	private XmlWriter xmlWriter;
	
	public ClassicLogger getLogger() throws ConfigurableServiceException, BrokerException, IOException{
		if(logger == null){
			logger = ClassicLogger.fromConfigurableService(getConfigurableService());
		}
		return logger;
	}
	
	
	public void setMessageId(String messageId){
		this.messageId = messageId;
	}
	
	public ConfigurableService getConfigurableService() throws ConfigurableServiceException{
		if(cs == null){
			cs = ConfigurableService.getInstance("ECOMMERCE_AUTHENTICATOR");
		}
		return cs;
	}
	
	public ObjectMapper getObjectMapper(){
		if(mapper == null){
			mapper = new ObjectMapper();
		}
		return mapper;
	}
	
	public StackTraceReceiver getStackTraceReceiver(){
		if(stackTraceReciever == null){
			stackTraceReciever = new StackTraceReceiver();
		}
		return stackTraceReciever;
	}
	
	public Validator getValidator(){
		if(validator == null){
			validator = Validation.byDefaultProvider().configure().messageInterpolator(new ParameterMessageInterpolator())
					.buildValidatorFactory().getValidator();
		}
		return validator;
	}
	
	public JsonDeserializer getJsonDeserializer(){
		if(jsonDeserializer == null){
			jsonDeserializer = new JsonDeserializer(this);
		}
		return jsonDeserializer;
	}
	
	public TokenService getTokenService() throws ConfigurableServiceException{
		if(tokenService == null){
			String key = getConfigurableService().get("secretKey");
			String issuer = getConfigurableService().get("issuer");
			Integer expirationHours = Integer.parseInt(getConfigurableService().get("expirationHours"));
			Integer expirationHoursForRemembered = Integer.parseInt(getConfigurableService().get("expirationHoursForRemembered"));
			tokenService = new TokenService(key, issuer, expirationHours, expirationHoursForRemembered);
		}
		return tokenService;
	}
	
	public XmlWriter getXmlWriter(){
		if(xmlWriter == null){
			xmlWriter = new XmlWriter();
		}
		return xmlWriter;
	}
}
