package ibar.task.ecommerce.merchantsapi.utils;

import ibar.task.ecommerce.merchantsapi.computes.BaseCompute;
import ibar.task.ecommerce.merchantsapi.dao.MerchantDao;

import java.io.IOException;

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
	private PasswordValidator passwordValidator;
	private JsonDeserializer jsonDeserializer;
	private XmlWriter xmlWriter;
	private BaseCompute.BaseJavaComputeEvaluator evaluator;
	
	public ApplicationContext(BaseCompute.BaseJavaComputeEvaluator evaluator){
		this.evaluator = evaluator;
	}
	
	public ClassicLogger getLogger() throws ConfigurableServiceException, BrokerException, IOException{
		if(logger == null){
			logger = ClassicLogger.fromConfigurableService(getConfigurableService());
		}
		return logger;
	}
	
	public MerchantDao getMerchantDao() throws ConfigurableServiceException, MbException, BrokerException, IOException{
		if(merchantDao == null ){
			merchantDao = new MerchantDao(evaluator.getConnectionByJdbcName(getConfigurableService().get("JDBCName")), getLogger(), evaluator.getMessageId(), getConfigurableService().get("PackageFullname"));
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
	
	public XmlWriter getXmlWriter(){
		if(xmlWriter == null){
			xmlWriter = new XmlWriter();
		}
		return xmlWriter;
	}
}
