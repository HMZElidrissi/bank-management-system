package ma.hmzelidrissi.bankmanagementsystem.dtos.loan;

public record LoanCriterias(
        int age,
        double monthlyIncome,
        int creditScore,
        double debtRatio,
        String collateralDescription,
        double amount,
        int termMonths
) {
}
