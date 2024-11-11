package ma.hmzelidrissi.bankmanagementsystem.repositories;

import ma.hmzelidrissi.bankmanagementsystem.entities.Transaction;
import ma.hmzelidrissi.bankmanagementsystem.enums.TransactionStatus;
import ma.hmzelidrissi.bankmanagementsystem.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByReference(String reference);

    Page<Transaction> findBySourceAccountId(Long accountId, Pageable pageable);

    Page<Transaction> findByDestinationAccountId(Long accountId, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.sourceAccount.id = :accountId OR t.destinationAccount.id = :accountId")
    Page<Transaction> findByAccountId(@Param("accountId") Long accountId, Pageable pageable);

    Page<Transaction> findByStatus(TransactionStatus status, Pageable pageable);

    Page<Transaction> findByType(TransactionType type, Pageable pageable);

    Page<Transaction> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.amount >= :minAmount")
    Page<Transaction> findLargeTransactions(@Param("minAmount") double minAmount, Pageable pageable);

    @Query("""
            SELECT t FROM Transaction t 
            WHERE (:accountId IS NULL OR t.sourceAccount.id = :accountId OR t.destinationAccount.id = :accountId)
            AND (:type IS NULL OR t.type = :type)
            AND (:status IS NULL OR t.status = :status)
            AND (:startDate IS NULL OR t.createdAt >= :startDate)
            AND (:endDate IS NULL OR t.createdAt <= :endDate)
            AND (:minAmount IS NULL OR t.amount >= :minAmount)
            AND (:maxAmount IS NULL OR t.amount <= :maxAmount)
            """)
    Page<Transaction> search(
            @Param("accountId") Long accountId,
            @Param("type") TransactionType type,
            @Param("status") TransactionStatus status,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("minAmount") Double minAmount,
            @Param("maxAmount") Double maxAmount,
            Pageable pageable
    );
}
