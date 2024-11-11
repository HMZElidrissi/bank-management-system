package ma.hmzelidrissi.bankmanagementsystem.mappers;

import ma.hmzelidrissi.bankmanagementsystem.documents.TransactionDocument;
import ma.hmzelidrissi.bankmanagementsystem.dtos.transaction.TransactionResponseDTO;
import ma.hmzelidrissi.bankmanagementsystem.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "feeAmount", expression = "java(transaction.getFeeAmount())")
    @Mapping(target = "totalAmount", expression = "java(transaction.getTotalAmount())")
    @Mapping(target = "sourceAccountOwnerName", source = "sourceAccount.user.name")
    @Mapping(target = "destinationAccountOwnerName", source = "destinationAccount.user.name")
    TransactionResponseDTO toDTO(Transaction transaction);

    @Mapping(target = "id", source = "transactionId")
    @Mapping(target = "type", expression = "java(TransactionType.valueOf(transaction.getType()))")
    @Mapping(target = "status", expression = "java(TransactionStatus.valueOf(transaction.getStatus()))")
    @Mapping(target = "sourceAccountOwnerName", ignore = true)
    @Mapping(target = "destinationAccountOwnerName", ignore = true)
    TransactionResponseDTO toDTO(TransactionDocument transaction);
}