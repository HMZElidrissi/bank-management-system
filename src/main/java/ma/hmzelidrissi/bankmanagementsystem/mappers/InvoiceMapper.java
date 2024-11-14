package ma.hmzelidrissi.bankmanagementsystem.mappers;

import ma.hmzelidrissi.bankmanagementsystem.dtos.invoice.CreateInvoiceRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.invoice.InvoiceResponseDTO;
import ma.hmzelidrissi.bankmanagementsystem.entities.Account;
import ma.hmzelidrissi.bankmanagementsystem.entities.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")

public interface InvoiceMapper {
    @Mapping(target = "account", source = "accountId", qualifiedByName = "accountIdToAccount")
    Invoice toEntity(CreateInvoiceRequestDTO request);

    @Mapping(target = "accountId", source = "account.id")
    InvoiceResponseDTO toDTO(Invoice invoice);

    @Named("accountIdToAccount")
    default Account accountIdToAccount(Long accountId) {
        return accountId == null ? null : Account.builder().id(accountId).build();
    }
}
