package ibar.task.ecommerce.products.dao;

import ibar.task.ecommerce.products.models.DeliveryOptions;
import ibar.task.ecommerce.products.models.InventoryItem;
import ibar.task.ecommerce.products.models.Product;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;
import ua.com.integrity.logging.ClassicLogger;

public class ProductsDao extends Dao{

	String packageFullName;
	public ProductsDao(Connection conn, ClassicLogger appLogger,
			String logMessageId, String packageFullName) {
		super(conn, appLogger, logMessageId);
		this.packageFullName = packageFullName;
	}

	public Product addProduct(Product product) throws SQLException{
		String sql = "{call " + packageFullName + ".add_product(?, ?, ?, ?, ?, ?)}";
		try (CallableStatement cls = conn.prepareCall(sql)) {
			cls.setString("p_name", product.getName());
			cls.setString("p_description", product.getDescription());
			cls.setBigDecimal("p_unit_price", product.getUnitPrice());
			cls.setLong("p_merchant_id", product.getMerchantId());
			cls.setString("p_product_category", product.getProductCategory());
			cls.registerOutParameter("p_product_id", OracleTypes.NUMBER);
			cls.execute();
			
			product.setId(cls.getLong("p_product_id"));
			addDeliveryOptions(product.getDeliveryOptions(), product.getId());
			for(InventoryItem item: product.getInventoryItems()){
				item = addInventoryItem(item, product.getId());
			}
			return product;
		}
	}
	
	public InventoryItem addInventoryItem(InventoryItem item, Long productId) throws SQLException{
		String sql = "{call " + packageFullName + ".add_inventory_item(?, ?, ?)}";
		try (CallableStatement cls = conn.prepareCall(sql)) {
			cls.setString("p_name", item.getName());
			cls.setLong("p_product_id", productId);
			cls.registerOutParameter("p_inventory_item_id", OracleTypes.NUMBER);
			cls.execute();
			
			item.setId(cls.getLong("p_inventory_item_id"));
			return item;
		}
	}
	
	public DeliveryOptions addDeliveryOptions(DeliveryOptions deliveryOptions, Long productId) throws SQLException{
		String sql = "{call " + packageFullName + ".add_delivery_options(?, ?)}";
		try (CallableStatement cls = conn.prepareCall(sql)) {
			cls.setString("p_delivery_options", deliveryOptions.getSomeDeliveryOption());
			cls.setLong("p_product_id", productId);
			cls.execute();
			return deliveryOptions;
		}
	}
}
