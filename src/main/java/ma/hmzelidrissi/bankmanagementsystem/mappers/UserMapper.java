package ma.hmzelidrissi.bankmanagementsystem.mappers;

import ma.hmzelidrissi.bankmanagementsystem.dtos.user.CreateUserRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.user.UpdateUserRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.user.UserResponseDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.user.UserSummaryDTO;
import ma.hmzelidrissi.bankmanagementsystem.entities.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(CreateUserRequestDTO request);

    UserResponseDTO toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateUserFromRequest(UpdateUserRequestDTO request, @MappingTarget User user);

    UserSummaryDTO toSummary(User user);
}