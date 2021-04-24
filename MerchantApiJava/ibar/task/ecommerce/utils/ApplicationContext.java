package ibar.task.ecommerce.utils;

import ibar.task.ecommerce.dao.MerchantDao;

import java.io.IOException;
import java.sql.Connection;

import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import ua.com.integrity.broker.BrokerException;
import ua.com.integrity.broker.ConfigurableService;
import ua.com.integrity.broker.ConfigurableServiceException;
import ua.com.integrity.logging.ClassicLogger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.broker.plugin.MbException;

public class ApplicationContext {
	private ObjectMapper mapper;
	private ConfigurableService cs;
	private StackTraceReceiver stackTraceReciever;
	private MerchantDao merchantDao;
	private Validator validator;
	private ClassicLogger logger;
	private String messageId;
	private Connection connection;
	private PasswordValidator passwordValidator;
	private JsonDeserializer jsonDeserializer;
	
	public ClassicLogger getLogger() throws ConfigurableServiceException, BrokerException, IOException{
		if(logger == null){
			logger = ClassicLogger.fromConfigurableService(getConfigurableService());
		}
		return logger;
	}
	
	
	public void setMessageId(String messageId){
		this.messageId = messageId;
	}
	
	public void setConnection(Connection connection){
		this.connection = connection;
	}
	
	public MerchantDao getMerchantDao() throws ConfigurableServiceException, MbException, BrokerException, IOException{
		if(merchantDao == null ){
			merchantDao = new MerchantDao(connection, getLogger(), messageId);
		}
		return merchantDao;
	}
	
	public ConfigurableService getConfigurableService() throws ConfigurableServiceException{
		if(cs == null){
			cs = ConfigurableService.getInstance("ECOMMERCE_MERCHANT");
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
	
	public PasswordValidator getPasswordValidator(){
		if(passwordValidator == null){
			passwordValidator = new PasswordValidator();
		}
		return passwordValidator;
	}
	
	public JsonDeserializer getJsonDeserializer(){
		if(jsonDeserializer == null){
			jsonDeserializer = new JsonDeserializer(this);
		}
		return jsonDeserializer;
	}
}
