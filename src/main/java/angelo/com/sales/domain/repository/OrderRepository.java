package angelo.com.sales.domain.repository;

import angelo.com.sales.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

   @Query("select p from Order p left join fetch p.orderItems where p.id = :id")
   Optional<Order> findByIdFetchItems(@Param("id") Long id);

}
