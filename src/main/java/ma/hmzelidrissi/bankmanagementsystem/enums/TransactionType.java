package ma.hmzelidrissi.bankmanagementsystem.enums;

import lombok.Getter;

@Getter
public enum TransactionType {
    CLASSIC_INTERNAL(0.0, 0.0, 0.0, false),              // Free internal classic transfers
    CLASSIC_EXTERNAL(0.5, 5.0, 50.0, true),              // 0.5% fee for external classic transfers
    INSTANT_INTERNAL(0.1, 1.0, 10.0, false),             // Small fee for instant internal transfers
    INSTANT_EXTERNAL(1.0, 10.0, 100.0, true),            // Higher fee for instant external transfers
    STANDING_ORDER_INTERNAL(0.0, 0.0, 0.0, false),       // Free internal standing orders
    STANDING_ORDER_EXTERNAL(0.3, 3.0, 30.0, true);       // Reduced fee for external standing orders

    private final double feePercentage;      // Fee percentage for the transaction
    private final double minimumFee;         // Minimum fee amount
    private final double maximumFee;         // Maximum fee amount
    private final boolean isExternalBank;     // Whether this type is for external bank transfers

    TransactionType(double feePercentage, double minimumFee, double maximumFee, boolean isExternalBank) {
        this.feePercentage = feePercentage;
        this.minimumFee = minimumFee;
        this.maximumFee = maximumFee;
        this.isExternalBank = isExternalBank;
    }

    public double calculateFee(double amount) {
        double calculatedFee = amount * (feePercentage / 100);

        if (calculatedFee < minimumFee) {
            return minimumFee;
        }

        return Math.min(calculatedFee, maximumFee);
    }

    public static TransactionType determineTransactionType(boolean isInstant, boolean isStandingOrder, boolean isExternal) {
        if (isStandingOrder) {
            return isExternal ? STANDING_ORDER_EXTERNAL : STANDING_ORDER_INTERNAL;
        } else if (isInstant) {
            return isExternal ? INSTANT_EXTERNAL : INSTANT_INTERNAL;
        } else {
            return isExternal ? CLASSIC_EXTERNAL : CLASSIC_INTERNAL;
        }
    }
}