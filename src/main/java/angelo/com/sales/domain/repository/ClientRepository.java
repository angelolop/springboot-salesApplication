package angelo.com.sales.domain.repository;

import angelo.com.sales.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
