package ibar.task.ecommerce.products.utils;

import java.util.Map;

import ibar.task.ecommerce.products.dao.QueryBuilder;
import ibar.task.ecommerce.products.exceptions.CommonException;
import ibar.task.ecommerce.products.exceptions.PageSizeLessOneException;
import ibar.task.ecommerce.products.exceptions.PageSizeNotNumberException;

public class ProductsQueryBuilder {
	public String getProductsQueryByParams(Map<String, String> params) throws CommonException {
		String baseQuery = "select  p.* from ecommerce_products p "
				+ "left join ecommerce_inventory_items i on p.id = i.product_id "
				+ "left join ecommerce_delivery_options d on p.id = d.id";
		QueryBuilder queryBuilder = new QueryBuilder(baseQuery).groupBy("p.id")
				.groupBy("p.name").groupBy("p.description").groupBy("p.merchant_id").groupBy("p.product_category")
				.groupBy("p.unit_price");
		for (Map.Entry<String, String> keyValue : params.entrySet()) {
			String key = keyValue.getKey();
			switch (key) {
			case "sortByPriceAsc":
				if (Boolean.valueOf(keyValue.getValue())) {
					queryBuilder = queryBuilder.orderBy("p.unit_price");
				}
				break;
			case "sortByPriceDesc":
				if (Boolean.valueOf(keyValue.getValue())) {
					queryBuilder = queryBuilder.orderBy("p.unit_price desc");
				}
				break;
			case "sortByInventorySizeAsc":
				if (Boolean.valueOf(keyValue.getValue())) {
					queryBuilder = queryBuilder.orderBy("count(i.id)");
				}
				break;
			case "sortByInventorySizeDesc":
				if (Boolean.valueOf(keyValue.getValue())) {
					queryBuilder = queryBuilder.orderBy("count(i.id) desc");
				}
				break;
			case "inventorySize_gth":
				queryBuilder = queryBuilder.having("count(i.id) > "
						+ keyValue.getValue());
				break;
			case "merchantId":
				queryBuilder = queryBuilder.where("p.merchant_id = "
						+ keyValue.getValue());
				break;
			case "pageSize":
				Long pageSize;
				try{
					pageSize = Long.parseLong(keyValue.getValue());
				}catch(NumberFormatException e){
					throw new PageSizeNotNumberException(e);
				}
				if(pageSize < 1){
					throw new PageSizeLessOneException();
				}
				queryBuilder = queryBuilder.setPageSize(pageSize);
				break;
			case "pageNumber":
				Long pageNumber;
				try{
					pageNumber = Long.parseLong(keyValue.getValue());
				}catch(NumberFormatException e){
					throw new PageSizeNotNumberException(e);
				}
				if(pageNumber < 1){
					throw new PageSizeLessOneException();
				}
				queryBuilder = queryBuilder.setPageNumber(pageNumber);
				break;
			}
		}
		return queryBuilder.getQuery();
	}
}
