package ma.hmzelidrissi.bankmanagementsystem.repositories;

import ma.hmzelidrissi.bankmanagementsystem.entities.RecurringTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecurringTransactionRepository extends JpaRepository<RecurringTransaction, Long> {
    List<RecurringTransaction> findByNextExecutionDateBeforeAndEndDateAfter(
            LocalDateTime nextExecutionDate,
            LocalDateTime currentDate
    );

    List<RecurringTransaction> findByTransactionSourceAccountId(Long accountId);

    @Query("""
            SELECT rt FROM RecurringTransaction rt 
            WHERE rt.nextExecutionDate <= :executionDate 
            AND (rt.endDate IS NULL OR rt.endDate > :currentDate)
            AND (rt.totalExecutions IS NULL OR rt.executedCount < rt.totalExecutions)
            """)
    List<RecurringTransaction> findDueRecurringTransactions(
            @Param("executionDate") LocalDateTime executionDate,
            @Param("currentDate") LocalDateTime currentDate
    );
}
