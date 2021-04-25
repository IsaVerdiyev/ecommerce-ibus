package ibar.task.ecommerce.products.dao;

import java.sql.Connection;

import ua.com.integrity.logging.ClassicLogger;

public class Dao {

	protected Connection conn;
	protected ClassicLogger appLogger;
	protected String messageId;

	public Dao(Connection conn, ClassicLogger appLogger, String logMessageId) {
		this.conn = conn;
		this.appLogger = appLogger;
		this.messageId = logMessageId;
	}
}
