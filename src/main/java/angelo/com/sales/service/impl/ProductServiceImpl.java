package angelo.com.sales.service.impl;

import angelo.com.sales.domain.entity.Product;
import angelo.com.sales.domain.repository.ProductRepository;
import angelo.com.sales.service.ProductService;
import org.springframework.data.domain.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

   @Autowired
   ProductRepository repository;

   public List<Product> findAllProducts() {
      return repository.findAll();
   }

   public Product findProductById(Long id) {
      return repository
              .findById(id)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
   }

   public List<Product> findProductByExample(Product productToFilter) {
      ExampleMatcher matcher = ExampleMatcher
                                .matching()
                                .withIgnoreCase()
                                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
      Example<Product> example = Example.of(productToFilter, matcher);
      return repository.findAll(example);
   }

   public Product insertProduct(Product product) {
      return repository.save(product);
   }

   public void deleteProductById(Long id) {
      repository.deleteById(id);
   }

   public void updateProductById(Product product, Long id) {
      repository
              .findById(id)
              .map(ProductToUpdate -> {
                 product.setId(ProductToUpdate.getId());
                 return insertProduct(product);
              }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found"));
   }
}
