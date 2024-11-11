package ma.hmzelidrissi.bankmanagementsystem.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.hmzelidrissi.bankmanagementsystem.enums.TransactionFrequency;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "recurring_transactions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecurringTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionFrequency frequency;

    @Column(name = "next_execution_date", nullable = false)
    private LocalDateTime nextExecutionDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "total_executions")
    private Integer totalExecutions;

    @Column(name = "executed_count")
    private Integer executedCount;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * The version of the entity. Used for optimistic locking.
     * i.e. preventing two users from updating the same entity simultaneously
     * by checking if the version has changed since it was last read.
     * If the version has changed, a OptimisticLockException is thrown.
     */
    @Version
    private Long version;
}