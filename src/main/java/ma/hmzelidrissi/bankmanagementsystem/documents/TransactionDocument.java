package ma.hmzelidrissi.bankmanagementsystem.documents;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "transactions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDocument {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String transactionId;

    @Field(type = FieldType.Keyword)
    private String type;

    @Field(type = FieldType.Double)
    private double amount;

    @Field(type = FieldType.Double)
    private double feeAmount;

    @Field(type = FieldType.Double)
    private double totalAmount;

    @Field(type = FieldType.Keyword)
    private String sourceAccountId;

    @Field(type = FieldType.Keyword)
    private String sourceAccountNumber;

    @Field(type = FieldType.Keyword)
    private String destinationAccountId;

    @Field(type = FieldType.Keyword)
    private String destinationAccountNumber;

    @Field(type = FieldType.Keyword)
    private String status;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Keyword)
    private String reference;

    @Field(type = FieldType.Date)
    private LocalDateTime createdAt;

    // Fields for recurring transactions
    @Field(type = FieldType.Boolean)
    private boolean recurring;

    @Field(type = FieldType.Keyword)
    private String frequency;
}