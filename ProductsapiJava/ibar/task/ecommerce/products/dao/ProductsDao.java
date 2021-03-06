package ibar.task.ecommerce.products.dao;

import ibar.task.ecommerce.products.models.DeliveryOptions;
import ibar.task.ecommerce.products.models.InventoryItem;
import ibar.task.ecommerce.products.models.Product;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import oracle.jdbc.OracleTypes;
import ua.com.integrity.logging.ClassicLogger;

public class ProductsDao extends Dao {

	String packageFullName;

	public ProductsDao(Connection conn, ClassicLogger appLogger,
			String logMessageId, String packageFullName) {
		super(conn, appLogger, logMessageId);
		this.packageFullName = packageFullName;
	}

	public Product addProduct(Product product) throws SQLException {
		String sql = "{call " + packageFullName
				+ ".add_product(?, ?, ?, ?, ?, ?)}";
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
			for (InventoryItem item : product.getInventoryItems()) {
				item = addInventoryItem(item, product.getId());
			}
			return product;
		}
	}

	public InventoryItem addInventoryItem(InventoryItem item, Long productId)
			throws SQLException {
		String sql = "{call " + packageFullName
				+ ".add_inventory_item(?, ?, ?)}";
		try (CallableStatement cls = conn.prepareCall(sql)) {
			cls.setString("p_name", item.getName());
			cls.setLong("p_product_id", productId);
			cls.registerOutParameter("p_inventory_item_id", OracleTypes.NUMBER);
			cls.execute();

			item.setId(cls.getLong("p_inventory_item_id"));
			return item;
		}
	}

	public DeliveryOptions addDeliveryOptions(DeliveryOptions deliveryOptions,
			Long productId) throws SQLException {
		String sql = "{call " + packageFullName
				+ ".add_delivery_options(?, ?)}";
		try (CallableStatement cls = conn.prepareCall(sql)) {
			cls.setString("p_delivery_options",
					deliveryOptions.getSomeDeliveryOption());
			cls.setLong("p_product_id", productId);
			cls.execute();
			return deliveryOptions;
		}
	}

	public List<Product> getProductsByQueryString(String query)
			throws SQLException {
		List<Product> products = new ArrayList<>();
		try (Statement cls = conn.createStatement()) {
			ResultSet rs = cls.executeQuery(query);
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getLong("id"));
				product.setName(rs.getString("name"));
				product.setDescription(rs.getString("description"));
				product.setMerchantId(rs.getLong("merchant_id"));
				product.setProductCategory(rs.getString("product_category"));
				product.setUnitPrice(rs.getBigDecimal("unit_price"));
				products.add(product);
			}
		}
		Map<Long, List<InventoryItem>> invenontoryItemsByProducts = getInventoryItemsOfProducts(products);
		Map<Long, DeliveryOptions> deliveryOptionsByProducts = getDeliveryOptionsOfProducts(products);
		for (Product product : products) {
			product.setInventoryItems(Optional.ofNullable(
					invenontoryItemsByProducts.get(product.getId())).orElse(
					new ArrayList<>()));
			product.setDeliveryOptions(deliveryOptionsByProducts.get(product
					.getId()));
		}
		return products;
	}

	public Map<Long, List<InventoryItem>> getInventoryItemsOfProducts(
			List<Product> products) throws SQLException {
		Map<Long, List<InventoryItem>> inventoryItemsByProductId = new HashMap<>();
		String productIdsStr = products.stream().map(p -> p.getId().toString())
				.collect(Collectors.joining(", "));
		String sql = "{call " + packageFullName
				+ ".get_inv_items_by_product_ids(?, ?)}";
		try (CallableStatement cls = conn.prepareCall(sql)) {
			cls.setString("p_product_ids", productIdsStr);
			cls.registerOutParameter("p_out_cursor", OracleTypes.CURSOR);
			cls.execute();
			ResultSet rs = (ResultSet) cls.getObject("p_out_cursor");
			while (rs.next()) {
				InventoryItem item = new InventoryItem();
				item.setId(rs.getLong("id"));
				item.setName(rs.getString("name"));
				Long productId = rs.getLong("product_id");
				if (!inventoryItemsByProductId.containsKey(productId)) {
					inventoryItemsByProductId.put(productId,
							new ArrayList<InventoryItem>());
				}
				inventoryItemsByProductId.get(productId).add(item);
			}
		}
		return inventoryItemsByProductId;
	}

	public Map<Long, DeliveryOptions> getDeliveryOptionsOfProducts(
			List<Product> products) throws SQLException {
		Map<Long, DeliveryOptions> deliveryOptionsByProductId = new HashMap<>();
		String productIdsStr = products.stream().map(p -> p.getId().toString())
				.collect(Collectors.joining(", "));
		String sql = "{call " + packageFullName
				+ ".get_deliv_opt_by_product_ids(?, ?)}";
		try (CallableStatement cls = conn.prepareCall(sql)) {
			cls.setString("p_product_ids", productIdsStr);
			cls.registerOutParameter("p_out_cursor", OracleTypes.CURSOR);
			cls.execute();
			ResultSet rs = (ResultSet) cls.getObject("p_out_cursor");
			while (rs.next()) {
				DeliveryOptions deliveryOptions = new DeliveryOptions();
				deliveryOptions.setSomeDeliveryOption(rs
						.getString("some_delivery_options"));
				Long productId = rs.getLong("product_id");
				deliveryOptionsByProductId.put(productId, deliveryOptions);
			}
		}
		return deliveryOptionsByProductId;
	}
}
