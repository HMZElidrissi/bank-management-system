package ma.hmzelidrissi.bankmanagementsystem.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSummaryDTO {
    Long id;
    String name;
    int age;
    double monthlyIncome;
    int creditScore;
}
