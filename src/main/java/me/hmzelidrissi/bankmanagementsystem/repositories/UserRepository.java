package me.hmzelidrissi.bankmanagementsystem.repositories;

import me.hmzelidrissi.bankmanagementsystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
