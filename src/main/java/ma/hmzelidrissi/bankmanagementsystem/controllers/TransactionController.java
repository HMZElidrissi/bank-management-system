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
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @Valid @RequestBody CreateTransactionRequestDTO request) {
        return ResponseEntity.ok(transactionService.createTransaction(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transaction by ID")
    // // @PreAuthorize("@securityService.canAccessTransaction(#id)")
    // @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @GetMapping("/reference/{reference}")
    @Operation(summary = "Get transaction by reference")
    // @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TransactionResponseDTO> getTransactionByReference(
            @PathVariable String reference) {
        return ResponseEntity.ok(transactionService.getTransactionByReference(reference));
    }

    @GetMapping("/search")
    @Operation(summary = "Search transactions")
    // @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<Page<TransactionResponseDTO>> searchTransactions(
            @Valid TransactionSearchCriteria criteria,
            Pageable pageable) {
        return ResponseEntity.ok(transactionService.searchTransactions(criteria, pageable));
    }

    @PostMapping("/{id}/approve")
    @Operation(summary = "Approve or reject a transaction")
    // @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<TransactionResponseDTO> approveTransaction(
            @PathVariable Long id,
            @Valid @RequestBody TransactionApprovalRequestDTO request) {
        return ResponseEntity.ok(transactionService.approveTransaction(id, request));
    }

    @GetMapping("/account/{accountId}")
    @Operation(summary = "Get transactions for an account")
    // @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<TransactionResponseDTO>> getAccountTransactions(
            @PathVariable Long accountId,
            Pageable pageable) {
        return ResponseEntity.ok(transactionService.getAccountTransactions(accountId, pageable));
    }
}