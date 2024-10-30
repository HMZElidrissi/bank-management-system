package ma.hmzelidrissi.bankmanagementsystem.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "invoices")
@Getter
@Setter
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amountDue;
    private LocalDate dueDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
