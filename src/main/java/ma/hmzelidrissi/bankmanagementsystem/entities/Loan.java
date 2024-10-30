package ma.hmzelidrissi.bankmanagementsystem.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "loans")
@Getter
@Setter
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double principal; // the initial amount of money
    private double interestRate;
    private int termMonths;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private boolean approved;
}
