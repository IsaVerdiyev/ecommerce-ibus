package ibar.task.ecommerce.products.computes;



import ibar.task.ecommerce.products.errors.ApiError;
import ibar.task.ecommerce.products.exceptions.CommonException;
import ibar.task.ecommerce.products.exceptions.UknownException;
import ibar.task.ecommerce.products.utils.ApplicationContext;

import java.sql.Connection;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.w3c.dom.NodeList;

import ua.com.integrity.ElementReference;
import ua.com.integrity.JavaComputeNode;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

public abstract class BaseCompute extends JavaComputeNode {

	protected String getComputeName() {
		return getClass().getSimpleName();
	}

	public abstract class BaseJavaComputeEvaluator extends JavaComputeEvaluator {

		ApplicationContext context;
		public ElementReference globalEnv;
		public ElementReference localEnv;
		
		public void init() throws Exception {
			context = new ApplicationContext(this);
			logger = context.getLogger();
			assignMessageId();
			context.getObjectMapper().setVisibility(PropertyAccessor.ALL, Visibility.NONE);
			context.getObjectMapper().setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			context.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			globalEnv = globalEnvironment();
			localEnv = localEnvironment();
			
			logger.info(messageId, "===============" + getComputeName() + "===============\n");
		}

		public void handleException(BaseJavaComputeEvaluator evaluator, Exception e) throws Exception {
			logger.error(messageId, "--------EXCEPTION OCCURED------------");
			logger.error(messageId, context.getStackTraceReceiver().getStackTrace(e));
			context.getObjectMapper().setSerializationInclusion(Include.NON_NULL);
			ApiError localError = null;
			if (e instanceof CommonException) {
				CommonException computeException = (CommonException) e;
				localError = computeException.getApiError();
			} else {
				localError = new UknownException().getApiError();
			}
			String outJson = context.getObjectMapper().writeValueAsString(localError);
			setJsonOutput(outJson);
			MbElement headerElement = getOutputRootElement().createElementAsFirstChild("HTTPReplyHeader");
			headerElement.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "X-Original-HTTP-Status-Code",
					localError.getStatusCode());
			propagateAlt();
		}

		public String generateRequestLog() throws Exception {
			MbElement headerElement = outputRoot().getMbElement().getFirstElementByPath("HTTPInputHeader");
			HashMap<String, String> headers = new HashMap<>();
			if (headerElement != null) {
				NodeList list = headerElement.getDOMNode().getChildNodes();
				for (int i = 0; i < list.getLength(); i++) {
					headers.put(list.item(i).getNodeName(), list.item(i).getTextContent());
				}
			}
			String serviceUrl = localEnvironment().getAsString("Destination/HTTP/RequestURL");
			String json = "";
			Object contentNode = outputRoot().get("BLOB/BLOB");
			if (contentNode != null) {
				json = new String((byte[]) outputRoot().get("BLOB/BLOB"));
			}
			String method = localEnvironment().getAsString("Destination/HTTP/RequestLine/Method");
			return "url: "
					+ serviceUrl
					+ "\nmethod: "
					+ method
					+ "\nheaders: \n"
					+ headers.entrySet().stream().map(x -> "\t" + x.getKey() + ": " + x.getValue())
							.collect(Collectors.joining("\n")) + "\ncontent: \n\t" + json;
		}
		
		public Connection getConnectionByJdbcName(String jdbcName) throws MbException{
			return getJDBCType4Connection(jdbcName, JDBC_TransactionType.MB_TRANSACTION_AUTO);
		}
	}

	public String getStrOrEmptyStrFromMbElement(MbElement element) throws MbException {
		if (element == null || element.getValueAsString().equals("NULL")) {
			return "";
		} else {
			return element.getValueAsString();
		}
	}
}