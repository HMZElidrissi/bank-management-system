package ma.hmzelidrissi.bankmanagementsystem.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.hmzelidrissi.bankmanagementsystem.enums.LoanStatus;
import ma.hmzelidrissi.bankmanagementsystem.enums.LoanType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int age;

    private double monthlyIncome;

    private int creditScore;

    private double debtRatio;

    private String collateralDescription;

    private LocalDate accountOpeningDate;

    private double amount;

    @Enumerated(EnumType.STRING)
    private LoanType type;

    private double interestRate;

    private int termMonths; // term in months

    private double monthlyPayment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by")
    private User reviewedBy;

    private String rejectionReason;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    private LocalDate startDate;

    private LocalDate endDate;
}
