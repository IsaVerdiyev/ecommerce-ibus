package ibar.task.ecommerce.products.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTraceReceiver {
	public String getStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
}
