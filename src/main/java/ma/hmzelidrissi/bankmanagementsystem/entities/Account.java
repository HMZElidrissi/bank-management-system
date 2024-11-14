package ma.hmzelidrissi.bankmanagementsystem.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.hmzelidrissi.bankmanagementsystem.enums.AccountStatus;

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
}
