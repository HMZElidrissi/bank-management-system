package ma.hmzelidrissi.bankmanagementsystem.enums;

public enum TransactionStatus {
    PENDING,          // Initial state
    PENDING_APPROVAL, // Waiting for employee approval
    APPROVED,         // Approved but not executed
    REJECTED,         // Rejected by employee
    COMPLETED,        // Successfully completed
    FAILED,           // Failed due to technical/business reasons
    CANCELLED         // Cancelled by user
}