package angelo.com.sales.rest.controller;

import angelo.com.sales.domain.entity.Product;
import angelo.com.sales.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

   @Autowired
   ProductServiceImpl service;

   @GetMapping
   public List<Product> findAllProducts(){
      return service.findAllProducts();
   }

   @GetMapping("/{id}")
   public Product findProductById(@PathVariable Long id){
      return service.findProductById(id);
   }

   @GetMapping("/filter")
   public List<Product> findProductByExample(Product productToFilter) {
      return service.findProductByExample(productToFilter);
   }

   @PostMapping
   public ResponseEntity<Product> insertProduct(@RequestBody @Valid Product product) {
      Product productInserted = service.insertProduct(product);
      URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productInserted.getId()).toUri();
      return ResponseEntity.created(uri).body(productInserted);
   }

   @ResponseStatus(NO_CONTENT)
   @DeleteMapping("/{id}")
   public void deleteProductById(@PathVariable Long id) {
      service.findProductById(id);
      service.deleteProductById(id);
   }

   @ResponseStatus(NO_CONTENT)
   @PutMapping("/{id}")
   public void updateProductById(@RequestBody @Valid Product product, @PathVariable Long id) {
      service.findProductById(id);
      service.updateProductById(product, id);
   }
}
