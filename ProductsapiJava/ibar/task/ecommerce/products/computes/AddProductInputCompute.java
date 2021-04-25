package ibar.task.ecommerce.products.computes;

import ibar.task.ecommerce.products.models.Product;

public class AddProductInputCompute extends AbstractInputCompute {

	@Override
	protected void compute(BaseJavaComputeEvaluator evaluator, String body)
			throws Exception {
		Product product = evaluator.context.getJsonDeserializer()
				.deserializeToObjectIfValid(body, Product.class);
		product = evaluator.context.getProductsDao().addProduct(product);
		String outJson = evaluator.context.getObjectMapper()
				.writeValueAsString(product);
		evaluator.setJsonOutput(outJson);
	}
}
