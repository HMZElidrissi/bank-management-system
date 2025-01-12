package ma.hmzelidrissi.bankmanagementsystem.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import ma.hmzelidrissi.bankmanagementsystem.enums.TransactionStatus;
import ma.hmzelidrissi.bankmanagementsystem.enums.TransactionType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_account_id")
    private Account destinationAccount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(unique = true)
    private String reference;

    private String description;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public double getFeeAmount() {
        return type.calculateFee(amount);
    }

    public double getTotalAmount() {
        return amount + getFeeAmount();
    }

    public boolean isExternal() {
        return type.isExternalBank();
    }

    public boolean isInstant() {
        return type == TransactionType.INSTANT_INTERNAL ||
                type == TransactionType.INSTANT_EXTERNAL;
    }

    public boolean requiresApproval() {
        // Example business rule: External transactions over 10000 require approval
        return isExternal() && amount > 10000;
    }
}