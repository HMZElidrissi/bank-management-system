package ma.hmzelidrissi.bankmanagementsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.hmzelidrissi.bankmanagementsystem.dtos.transaction.CreateTransactionRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.transaction.TransactionApprovalRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.transaction.TransactionResponseDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.transaction.TransactionSearchCriteria;
import ma.hmzelidrissi.bankmanagementsystem.services.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Transaction Management", description = "APIs for managing transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @Operation(summary = "Create a new transaction")
    // @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponseDTO createTransaction(
            @Valid @RequestBody CreateTransactionRequestDTO request) {
        return transactionService.createTransaction(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transaction by ID")
    // @PreAuthorize("@securityService.canAccessTransaction(#id)")
    // @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    public TransactionResponseDTO getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @GetMapping("/reference/{reference}")
    @Operation(summary = "Get transaction by reference")
    // @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public TransactionResponseDTO getTransactionByReference(
            @PathVariable String reference) {
        return transactionService.getTransactionByReference(reference);
    }

    @GetMapping("/search")
    @Operation(summary = "Search transactions")
    // @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    public Page<TransactionResponseDTO> searchTransactions(
            @Valid TransactionSearchCriteria criteria,
            Pageable pageable) {
        return transactionService.searchTransactions(criteria, pageable);
    }

    @PostMapping("/{id}/approve")
    @Operation(summary = "Approve or reject a transaction")
    // @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    public TransactionResponseDTO approveTransaction(
            @PathVariable Long id,
            @Valid @RequestBody TransactionApprovalRequestDTO request) {
        return transactionService.approveTransaction(id, request);
    }

    @GetMapping("/account/{accountId}")
    @Operation(summary = "Get transactions for an account")
    // @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public Page<TransactionResponseDTO> getAccountTransactions(
            @PathVariable Long accountId,
            Pageable pageable) {
        return transactionService.getAccountTransactions(accountId, pageable);
    }
}