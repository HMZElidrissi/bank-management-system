package ma.hmzelidrissi.bankmanagementsystem.services;

import ma.hmzelidrissi.bankmanagementsystem.dtos.transaction.CreateTransactionRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.transaction.TransactionApprovalRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.transaction.TransactionResponseDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.transaction.TransactionSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {
    TransactionResponseDTO createTransaction(CreateTransactionRequestDTO request);

    TransactionResponseDTO getTransactionById(Long id);

    TransactionResponseDTO getTransactionByReference(String reference);

    Page<TransactionResponseDTO> getAccountTransactions(Long accountId, Pageable pageable);

    Page<TransactionResponseDTO> searchTransactions(TransactionSearchCriteria criteria, Pageable pageable);

    TransactionResponseDTO approveTransaction(Long id, TransactionApprovalRequestDTO request);

    void processRecurringTransactions();
}