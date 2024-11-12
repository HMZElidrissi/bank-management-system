package ma.hmzelidrissi.bankmanagementsystem.services;

import ma.hmzelidrissi.bankmanagementsystem.dtos.loan.CreateLoanRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.loan.LoanCriterias;
import ma.hmzelidrissi.bankmanagementsystem.dtos.loan.LoanResponseDTO;
import ma.hmzelidrissi.bankmanagementsystem.enums.LoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoanService {
    LoanResponseDTO applyForLoan(CreateLoanRequestDTO request);

    LoanResponseDTO getLoanById(Long id);

    Page<LoanResponseDTO> getMyLoans(Pageable pageable);

    Page<LoanResponseDTO> getAllLoans(Pageable pageable);

    void deleteLoan(Long id);

    void updateLoanStatus(Long id, LoanStatus status);

    LoanCriterias checkLoanEligibility(CreateLoanRequestDTO request);
}
