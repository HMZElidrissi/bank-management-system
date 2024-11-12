package ma.hmzelidrissi.bankmanagementsystem.dtos.loan;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ma.hmzelidrissi.bankmanagementsystem.enums.LoanType;

import java.time.LocalDate;

@Builder
public record CreateLoanRequestDTO(
        @NotNull(message = "Age is required")
        @Min(value = 18, message = "Age must be greater than or equal to 18")
        int age,

        @NotNull(message = "Monthly income is required")
        double monthlyIncome,

        @NotNull(message = "Credit score is required")
        int creditScore,

        @NotNull(message = "Debt ratio is required")
        double debtRatio,

        @NotNull(message = "Collateral is required")
        String collateralDescription,

        @NotNull(message = "Account opening date is required")
        LocalDate accountOpeningDate,

        @NotNull(message = "Amount is required")
        double amount,

        @NotNull(message = "Loan type is required")
        LoanType type,

        @NotNull(message = "Term in months is required")
        @Min(value = 1, message = "Term in months must be greater than 0")
        int termMonths,

        @NotNull(message = "Monthly payment is required")
        double monthlyPayment,

        @NotNull(message = "Start date is required")
        LocalDate startDate,

        @NotNull(message = "End date is required")
        LocalDate endDate
) {
}
