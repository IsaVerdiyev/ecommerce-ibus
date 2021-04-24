package ibar.task.ecommerce.dao;

import ibar.task.ecommerce.models.Merchant;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;
import ua.com.integrity.logging.ClassicLogger;

public class MerchantDao extends Dao{

	public MerchantDao(Connection conn, ClassicLogger appLogger,
			String logMessageId) {
		super(conn, appLogger, logMessageId);
	}

	public Merchant addMerchant(Merchant merchant) throws SQLException{
		String sql = "{call atbdwh.e_commerce.add_merchant(?, ?, ?, ?, ?, ?, ?, ?)}";
		try (CallableStatement cls = conn.prepareCall(sql)) {
			cls.setString("p_name", merchant.getName());
			cls.setString("p_type", merchant.getType());
			cls.setString("p_owner_name", merchant.getOwnerName());
			cls.setString("p_address", merchant.getAddress());
			cls.setString("p_phone_number", merchant.getPhoneNumber());
			cls.setString("p_email_address", merchant.getEmailAddress());
			cls.setString("p_password", merchant.getPassword());
			cls.registerOutParameter("p_merchant_id", OracleTypes.NUMBER);
			cls.execute();
			
			merchant.setId(cls.getLong("p_merchant_id"));
			return merchant;
		}
	}
	
	public Merchant findByName(String name) throws SQLException{
		Merchant merchant = null;
		String sql = " {call atbdwh.e_commerce.get_merchant_by_name(?, ?)}";
		try (CallableStatement cls = conn.prepareCall(sql)) {
			cls.setString("p_name", name);
			cls.registerOutParameter("p_out_cursor", OracleTypes.CURSOR);
			cls.execute();
			ResultSet rs = (ResultSet) cls.getObject("p_out_cursor");
			while (rs.next()) {
				merchant = new Merchant();
				merchant.setId(rs.getLong("merchant_id"));
				merchant.setAddress(rs.getString("address"));
				merchant.setName(rs.getString("name"));
				merchant.setEmailAddress(rs.getString("email_address"));
				merchant.setOwnerName(rs.getString("owner_name"));
				merchant.setPassword(rs.getString("password"));
				merchant.setPhoneNumber(rs.getString("phone_number"));
				merchant.setType(rs.getString("type"));
			}
		} 
		return merchant;
	}
}
