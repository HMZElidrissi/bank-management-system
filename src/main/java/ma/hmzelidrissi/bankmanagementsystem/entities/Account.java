package ma.hmzelidrissi.bankmanagementsystem.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.hmzelidrissi.bankmanagementsystem.enums.AccountStatus;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double balance;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "sourceAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> sourceTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "destinationAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> destinationTransactions = new ArrayList<>();
}
