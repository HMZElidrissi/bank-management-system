package ma.hmzelidrissi.bankmanagementsystem.repositories;

import ma.hmzelidrissi.bankmanagementsystem.documents.TransactionDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.time.LocalDateTime;

public interface TransactionElasticsearchRepository extends ElasticsearchRepository<TransactionDocument, String> {
    Page<TransactionDocument> findBySourceAccountIdOrDestinationAccountId(
            String sourceAccountId,
            String destinationAccountId,
            Pageable pageable
    );

    Page<TransactionDocument> findByAmountBetween(double minAmount, double maxAmount, Pageable pageable);

    @Query("{" +
            "\"bool\": {" +
            "  \"must\": [" +
            "    {\"range\": {\"createdAt\": {\"gte\": \"?0\", \"lte\": \"?1\"}}}," +
            "    {\"term\": {\"sourceAccountId\": \"?2\"}}" +
            "  ]" +
            "}}")
    Page<TransactionDocument> findByDateRangeAndAccount(
            LocalDateTime startDate,
            LocalDateTime endDate,
            String accountId,
            Pageable pageable
    );
}