package angelo.com.sales.service;

import angelo.com.sales.domain.entity.Product;

import java.util.List;

public interface ProductService {

   List<Product> findAllProducts();

   Product findProductById(Long id);

   List<Product> findProductByExample(Product productToFilter);

   Product insertProduct(Product product);

   void deleteProductById(Long id);

   void updateProductById(Product product, Long id);
}
