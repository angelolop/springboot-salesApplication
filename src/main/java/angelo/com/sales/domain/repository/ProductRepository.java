package angelo.com.sales.domain.repository;

import angelo.com.sales.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
