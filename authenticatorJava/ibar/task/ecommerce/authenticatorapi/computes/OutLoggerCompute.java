package ibar.task.ecommerce.authenticatorapi.computes;

import java.util.HashMap;
import java.util.stream.Collectors;

import org.w3c.dom.NodeList;

import com.ibm.broker.plugin.MbElement;

public class OutLoggerCompute extends BaseCompute {

	@Override
	protected JavaComputeEvaluator getEvaluator() {
		return new BaseJavaComputeEvaluator() {

			@Override
			public boolean evaluate() throws Exception {

				try {
					init();

					outputRoot().set(inputRoot());
					String outJson = "";
					MbElement outElement = outputRoot().getMbElement()
							.getFirstElementByPath("BLOB");
					if (outElement != null) {
						outJson = new String(outElement.toBitstream("", "", "",
								1208, 1208, 0));
					}

					String statusCode = null;

					MbElement mbelement = inputRoot()
							.getMbElement()
							.getFirstElementByPath(
									"HTTPReplyHeader/X-Original-HTTP-Status-Code");

					if (mbelement != null) {
						statusCode = mbelement.getValueAsString();
					} else {
						statusCode = "200";
					}

					MbElement headerElement = outputRoot().getMbElement()
							.getFirstElementByPath("HTTPReplyHeader");
					HashMap<String, String> headers = new HashMap<>();
					if (headerElement != null) {
						NodeList list = headerElement.getDOMNode()
								.getChildNodes();
						for (int i = 0; i < list.getLength(); i++) {
							headers.put(list.item(i).getNodeName(), list
									.item(i).getTextContent());
						}
					}

					String result = "";

					result += "status code : "
							+ statusCode
							+ "\n headers: \n"
							+ headers
									.entrySet()
									.stream()
									.map(x -> "\t" + x.getKey() + ": "
											+ x.getValue())
									.collect(Collectors.joining("\n"))
							+ "\n\ncontent : \n" + outJson;
					context.getLogger().info(messageId,
							"outResponse: \n" + result);
					propagate();
				} catch (Exception e) {
					handleException(this, e);
				}

				return false;
			}
		};
	}

}