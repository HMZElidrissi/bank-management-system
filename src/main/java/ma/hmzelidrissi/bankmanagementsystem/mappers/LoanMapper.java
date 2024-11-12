package ma.hmzelidrissi.bankmanagementsystem.mappers;

import ma.hmzelidrissi.bankmanagementsystem.dtos.loan.CreateLoanRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.loan.LoanResponseDTO;
import ma.hmzelidrissi.bankmanagementsystem.entities.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "interestRate", ignore = true)
    @Mapping(target = "reviewedBy", ignore = true)
    Loan toEntity(CreateLoanRequestDTO request);

    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "reviewedBy", ignore = true)
    @Mapping(target = "rejectionReason", ignore = true)
    @Mapping(target = "loanStartDate", source = "startDate")
    @Mapping(target = "loanEndDate", ignore = true)
    LoanResponseDTO toDTO(Loan loan);
}
