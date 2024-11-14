package ma.hmzelidrissi.bankmanagementsystem.dtos.invoice;

import ma.hmzelidrissi.bankmanagementsystem.enums.InvoiceStatus;

import java.time.LocalDate;

public record InvoiceResponseDTO(
        Long id,
        double amount,
        LocalDate dueDate,
        LocalDate paymentDate,
        InvoiceStatus status,
        String description,
        Long accountId
) {
}
