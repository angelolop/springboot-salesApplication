package angelo.com.sales.domain.repository;

import angelo.com.sales.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

   Optional<Users> findByLogin(String Login);

}
