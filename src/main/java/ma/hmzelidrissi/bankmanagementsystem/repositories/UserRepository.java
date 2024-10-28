package ma.hmzelidrissi.bankmanagementsystem.repositories;

import ma.hmzelidrissi.bankmanagementsystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
