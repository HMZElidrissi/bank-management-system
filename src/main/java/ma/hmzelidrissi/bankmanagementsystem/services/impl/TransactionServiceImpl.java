package ma.hmzelidrissi.bankmanagementsystem.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.hmzelidrissi.bankmanagementsystem.documents.TransactionDocument;
import ma.hmzelidrissi.bankmanagementsystem.dtos.transaction.CreateTransactionRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.transaction.TransactionApprovalRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.transaction.TransactionResponseDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.transaction.TransactionSearchCriteria;
import ma.hmzelidrissi.bankmanagementsystem.entities.Account;
import ma.hmzelidrissi.bankmanagementsystem.entities.RecurringTransaction;
import ma.hmzelidrissi.bankmanagementsystem.entities.Transaction;
import ma.hmzelidrissi.bankmanagementsystem.entities.TransactionApproval;
import ma.hmzelidrissi.bankmanagementsystem.enums.TransactionFrequency;
import ma.hmzelidrissi.bankmanagementsystem.enums.TransactionStatus;
import ma.hmzelidrissi.bankmanagementsystem.enums.TransactionType;
import ma.hmzelidrissi.bankmanagementsystem.exceptions.InsufficientFundsException;
import ma.hmzelidrissi.bankmanagementsystem.exceptions.ResourceNotFoundException;
import ma.hmzelidrissi.bankmanagementsystem.mappers.TransactionMapper;
import ma.hmzelidrissi.bankmanagementsystem.repositories.RecurringTransactionRepository;
import ma.hmzelidrissi.bankmanagementsystem.repositories.TransactionApprovalRepository;
import ma.hmzelidrissi.bankmanagementsystem.repositories.TransactionElasticsearchRepository;
import ma.hmzelidrissi.bankmanagementsystem.repositories.TransactionRepository;
import ma.hmzelidrissi.bankmanagementsystem.services.AccountService;
import ma.hmzelidrissi.bankmanagementsystem.services.TransactionService;
import ma.hmzelidrissi.bankmanagementsystem.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionElasticsearchRepository elasticsearchRepository;
    private final RecurringTransactionRepository recurringTransactionRepository;
    private final TransactionApprovalRepository approvalRepository;
    private final AccountService accountService;
    private final UserService userService;
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionResponseDTO createTransaction(CreateTransactionRequestDTO request) {
        // Validate accounts
        Account sourceAccount = accountService.getAccountById(request.sourceAccountId());
        Account destinationAccount = request.destinationAccountId() != null ?
                accountService.getAccountById(request.destinationAccountId()) : null;

        // Determine transaction type
        TransactionType type = TransactionType.determineTransactionType(
                request.instant(),
                request.recurring(),
                request.external()
        );

        // Create transaction
        Transaction transaction = Transaction.builder()
                .type(type)
                .amount(request.amount())
                .sourceAccount(sourceAccount)
                .destinationAccount(destinationAccount)
                .status(TransactionStatus.PENDING)
                .description(request.description())
                .reference(generateReference())
                .build();

        transaction = transactionRepository.save(transaction);

        // Create recurring transaction if needed
        if (request.recurring()) {
            RecurringTransaction recurringTransaction = RecurringTransaction.builder()
                    .transaction(transaction)
                    .frequency(request.frequency())
                    .nextExecutionDate(calculateNextExecutionDate(request.frequency()))
                    .endDate(request.endDate())
                    .totalExecutions(request.totalExecutions())
                    .executedCount(0)
                    .build();

            recurringTransactionRepository.save(recurringTransaction);
        }

        // Index in Elasticsearch
        saveToElasticsearch(transaction);

        return transactionMapper.toDTO(transaction);
    }

    private void saveToElasticsearch(Transaction transaction) {
        try {
            TransactionDocument document = TransactionDocument.builder()
                    .transactionId(transaction.getId().toString())
                    .type(transaction.getType().name())
                    .amount(transaction.getAmount())
                    .feeAmount(transaction.getFeeAmount())
                    .totalAmount(transaction.getTotalAmount())
                    .sourceAccountId(transaction.getSourceAccount().getId().toString())
                    .destinationAccountId(transaction.getDestinationAccount() != null ?
                            transaction.getDestinationAccount().getId().toString() : null)
                    .status(transaction.getStatus().name())
                    .description(transaction.getDescription())
                    .reference(transaction.getReference())
                    .createdAt(transaction.getCreatedAt())
                    .build();

            elasticsearchRepository.save(document);
        } catch (Exception e) {
            log.error("Failed to index transaction in Elasticsearch", e);
        }
    }

    @Override
    public TransactionResponseDTO getTransactionById(Long id) {
        return null;
    }

    @Override
    public TransactionResponseDTO getTransactionByReference(String reference) {
        return null;
    }

    @Override
    public Page<TransactionResponseDTO> getAccountTransactions(Long accountId, Pageable pageable) {
        try {
            Page<TransactionDocument> esTransactions = elasticsearchRepository
                    .findBySourceAccountIdOrDestinationAccountId(
                            accountId.toString(),
                            accountId.toString(),
                            pageable
                    );
            return esTransactions.map(transactionMapper::toDTO);
        } catch (Exception e) {
            log.warn("Failed to fetch transactions from Elasticsearch, falling back to database", e);
            // Fallback to database
            Page<Transaction> transactions = transactionRepository.findByAccountId(accountId, pageable);
            return transactions.map(transactionMapper::toDTO);
        }
    }

    @Override
    public Page<TransactionResponseDTO> searchTransactions(TransactionSearchCriteria criteria, Pageable pageable) {
        try {
            // Use Elasticsearch for search operations
            if (criteria.startDate() != null && criteria.endDate() != null && criteria.accountId() != null) {
                Page<TransactionDocument> esTransactions = elasticsearchRepository
                        .findByDateRangeAndAccount(
                                criteria.startDate(),
                                criteria.endDate(),
                                criteria.accountId().toString(),
                                pageable
                        );
                return esTransactions.map(transactionMapper::toDTO);
            }

            if (criteria.minAmount() != null && criteria.maxAmount() != null) {
                Page<TransactionDocument> esTransactions = elasticsearchRepository
                        .findByAmountBetween(
                                criteria.minAmount(),
                                criteria.maxAmount(),
                                pageable
                        );
                return esTransactions.map(transactionMapper::toDTO);
            }
        } catch (Exception e) {
            log.warn("Failed to search transactions in Elasticsearch, falling back to database", e);
        }

        // Fallback to database search
        Page<Transaction> transactions = transactionRepository.search(
                criteria.accountId(),
                criteria.type(),
                criteria.status(),
                criteria.startDate(),
                criteria.endDate(),
                criteria.minAmount(),
                criteria.maxAmount(),
                pageable
        );
        return transactions.map(transactionMapper::toDTO);
    }

    @Override
    public TransactionResponseDTO approveTransaction(Long id, TransactionApprovalRequestDTO request) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        if (!transaction.requiresApproval()) {
            throw new IllegalStateException("This transaction doesn't require approval");
        }

        if (transaction.getStatus() != TransactionStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("Transaction is not in PENDING_APPROVAL status");
        }

        TransactionApproval approval = TransactionApproval.builder()
                .transaction(transaction)
                .approvedBy(userService.getCurrentUser())
                .approved(request.approved())
                .comment(request.comment())
                .build();

        approvalRepository.save(approval);

        if (request.approved()) {
            transaction.setStatus(TransactionStatus.APPROVED);
            processTransaction(transaction);
        } else {
            transaction.setStatus(TransactionStatus.REJECTED);
        }

        transaction = transactionRepository.save(transaction);

        // Update Elasticsearch
        saveToElasticsearch(transaction);

        return transactionMapper.toDTO(transaction);

    }

    private void processTransaction(Transaction transaction) {
        Account sourceAccount = transaction.getSourceAccount();
        Account destinationAccount = transaction.getDestinationAccount();

        // Verify sufficient funds
        if (sourceAccount.getBalance() < transaction.getTotalAmount()) {
            throw new InsufficientFundsException("Insufficient funds in source account");
        }

        // Update account balances
        sourceAccount.setBalance(sourceAccount.getBalance() - transaction.getTotalAmount());
        destinationAccount.setBalance(destinationAccount.getBalance() + transaction.getAmount());

        // Update transaction status
        transaction.setStatus(TransactionStatus.COMPLETED);
    }

    @Override
    @Scheduled(cron = "0 0 * * * *") // Run every hour
    public void processRecurringTransactions() {
        LocalDateTime now = LocalDateTime.now();
        List<RecurringTransaction> dueTransactions = recurringTransactionRepository
                .findDueRecurringTransactions(now, now);

        for (RecurringTransaction recurring : dueTransactions) {
            try {
                // Create new transaction based on the original
                Transaction originalTransaction = recurring.getTransaction();
                Transaction newTransaction = Transaction.builder()
                        .type(originalTransaction.getType())
                        .amount(originalTransaction.getAmount())
                        .sourceAccount(originalTransaction.getSourceAccount())
                        .destinationAccount(originalTransaction.getDestinationAccount())
                        .description(originalTransaction.getDescription())
                        .reference(generateReference())
                        .status(TransactionStatus.PENDING)
                        .build();

                transactionRepository.save(newTransaction);

                // Update recurring transaction
                recurring.setExecutedCount(recurring.getExecutedCount() + 1);
                recurring.setNextExecutionDate(calculateNextExecutionDate(recurring.getFrequency()));
                recurringTransactionRepository.save(recurring);
            } catch (Exception e) {
                log.error("Failed to process recurring transaction: " + recurring.getId(), e);
            }
        }
    }

    private String generateReference() {
        return UUID.randomUUID().toString();
    }

    private LocalDateTime calculateNextExecutionDate(TransactionFrequency frequency) {
        LocalDateTime now = LocalDateTime.now();
        return switch (frequency) {
            case DAILY -> now.plusDays(1);
            case WEEKLY -> now.plusWeeks(1);
            case MONTHLY -> now.plusMonths(1);
            case QUARTERLY -> now.plusMonths(3);
            case YEARLY -> now.plusYears(1);
        };
    }
}