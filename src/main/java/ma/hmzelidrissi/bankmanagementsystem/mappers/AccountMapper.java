package ma.hmzelidrissi.bankmanagementsystem.mappers;

import ma.hmzelidrissi.bankmanagementsystem.dtos.account.AccountResponseDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.account.CreateAccountRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.account.UpdateAccountRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.entities.Account;
import ma.hmzelidrissi.bankmanagementsystem.enums.AccountStatus;
import ma.hmzelidrissi.bankmanagementsystem.entities.User;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        imports = {AccountStatus.class}
)
public interface AccountMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "balance", source = "initialBalance")
    @Mapping(target = "status", expression = "java(AccountStatus.ACTIVE)")
    @Mapping(target = "user", source = "userId", qualifiedByName = "userIdToUser")
    Account toEntity(CreateAccountRequestDTO request);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "userEmail", source = "user.email")
    AccountResponseDTO toResponse(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "balance", ignore = true)
    void updateAccountStatus(@MappingTarget Account account, UpdateAccountRequestDTO request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "balance", expression = "java(account.getBalance() + request.amount())")
    void updateAccountBalance(@MappingTarget Account account, UpdateAccountRequestDTO request);
    // we can use the @Named with @Mapping(target = "balance", source = ".", qualifiedByName = "updateBalance")
    // the dot (.) means "use the entire source object"

    @Named("userIdToUser")
    default User userIdToUser(Long userId) {
        return userId == null ? null : User.builder().id(userId).build();
    }
}
