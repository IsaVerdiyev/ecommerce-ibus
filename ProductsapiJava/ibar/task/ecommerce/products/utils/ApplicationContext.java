package ibar.task.ecommerce.products.utils;

import ibar.task.ecommerce.products.computes.BaseCompute;
import ibar.task.ecommerce.products.dao.ProductsDao;

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
	private ProductsDao productsDao;
	private Validator validator;
	private ClassicLogger logger;
	private JsonDeserializer jsonDeserializer;
	private XmlWriter xmlWriter;
	private BaseCompute.BaseJavaComputeEvaluator evaluator;
	private ProductsQueryBuilder productsQueryBuilder;

	public ApplicationContext(BaseCompute.BaseJavaComputeEvaluator evaluator) {
		this.evaluator = evaluator;
	}

	public ClassicLogger getLogger() throws ConfigurableServiceException,
			BrokerException, IOException {
		if (logger == null) {
			logger = ClassicLogger
					.fromConfigurableService(getConfigurableService());
		}
		return logger;
	}

	public ProductsDao getProductsDao() throws ConfigurableServiceException,
			MbException, BrokerException, IOException {
		if (productsDao == null) {
			productsDao = new ProductsDao(
					evaluator.getConnectionByJdbcName(getConfigurableService()
							.get("JDBCName")), getLogger(),
					evaluator.getMessageId(), getConfigurableService().get(
							"PackageFullname"));
		}
		return productsDao;
	}

	public ConfigurableService getConfigurableService()
			throws ConfigurableServiceException {
		if (cs == null) {
			cs = ConfigurableService.getInstance("ECOMMERCE_PRODUCTS");
		}
		return cs;
	}

	public ObjectMapper getObjectMapper() {
		if (mapper == null) {
			mapper = new ObjectMapper();
		}
		return mapper;
	}

	public StackTraceReceiver getStackTraceReceiver() {
		if (stackTraceReciever == null) {
			stackTraceReciever = new StackTraceReceiver();
		}
		return stackTraceReciever;
	}

	public Validator getValidator() {
		if (validator == null) {
			validator = Validation.byDefaultProvider().configure()
					.messageInterpolator(new ParameterMessageInterpolator())
					.buildValidatorFactory().getValidator();
		}
		return validator;
	}

	public JsonDeserializer getJsonDeserializer() {
		if (jsonDeserializer == null) {
			jsonDeserializer = new JsonDeserializer(this);
		}
		return jsonDeserializer;
	}

	public XmlWriter getXmlWriter() {
		if (xmlWriter == null) {
			xmlWriter = new XmlWriter();
		}
		return xmlWriter;
	}

	public ProductsQueryBuilder getProductsQueryBuilder() {
		if (productsQueryBuilder == null) {
			productsQueryBuilder = new ProductsQueryBuilder();
		}
		return productsQueryBuilder;
	}
}
