package ma.hmzelidrissi.bankmanagementsystem.repositories;

import ma.hmzelidrissi.bankmanagementsystem.dtos.account.AccountResponseDTO;
import ma.hmzelidrissi.bankmanagementsystem.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByUserId(Long userId);
}
