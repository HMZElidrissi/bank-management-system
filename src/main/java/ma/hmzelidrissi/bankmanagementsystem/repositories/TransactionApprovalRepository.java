package ma.hmzelidrissi.bankmanagementsystem.repositories;

import ma.hmzelidrissi.bankmanagementsystem.entities.TransactionApproval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionApprovalRepository extends JpaRepository<TransactionApproval, Long> {
    List<TransactionApproval> findByTransactionId(Long transactionId);

    Optional<TransactionApproval> findLatestByTransactionId(Long transactionId);
}