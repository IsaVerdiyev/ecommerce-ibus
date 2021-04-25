package ibar.task.ecommerce.products.computes;

import ibar.task.ecommerce.products.models.Product;

import java.util.List;
import java.util.Map;

public class GetProductsInputCompute extends AbstractInputCompute<String> {

	@Override
	protected void compute(BaseJavaComputeEvaluator evaluator, String body)
			throws Exception {
		Map<String, String> params = evaluator.getQueryParamsMap();
		String productsQuery = evaluator.context.getProductsQueryBuilder()
				.getProductsQueryByParams(params);
		logger.info(evaluator.getMessageId(), "productsQuery: " + productsQuery);
		List<Product> products = evaluator.context.getProductsDao()
				.getProductsByQueryString(productsQuery);
		evaluator.setJsonOutput(evaluator.context.getObjectMapper()
				.writeValueAsString(products));
	}

}