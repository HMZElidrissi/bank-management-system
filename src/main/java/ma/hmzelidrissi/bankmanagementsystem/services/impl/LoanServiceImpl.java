package ma.hmzelidrissi.bankmanagementsystem.services.impl;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import ma.hmzelidrissi.bankmanagementsystem.dtos.loan.LoanCriterias;
import ma.hmzelidrissi.bankmanagementsystem.entities.Loan;
import ma.hmzelidrissi.bankmanagementsystem.entities.User;
import ma.hmzelidrissi.bankmanagementsystem.exceptions.IneligibleForLoanException;
import ma.hmzelidrissi.bankmanagementsystem.exceptions.ResourceNotFoundException;
import ma.hmzelidrissi.bankmanagementsystem.mappers.LoanMapper;
import ma.hmzelidrissi.bankmanagementsystem.repositories.LoanRepository;
import ma.hmzelidrissi.bankmanagementsystem.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ma.hmzelidrissi.bankmanagementsystem.dtos.loan.CreateLoanRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.loan.LoanResponseDTO;
import ma.hmzelidrissi.bankmanagementsystem.enums.LoanStatus;
import ma.hmzelidrissi.bankmanagementsystem.services.LoanService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final UserService userService;
    private final LoanMapper loanMapper;

    @Override
    public LoanResponseDTO applyForLoan(CreateLoanRequestDTO request) {
        Loan loan = loanMapper.toEntity(request);
        loan.setStatus(LoanStatus.PENDING);
        User user = userService.getCurrentUser();
        loan.setUser(user);
        loanRepository.save(loan);
        return loanMapper.toDTO(loan);
    }

    @Override
    public LoanResponseDTO getLoanById(Long id) {
        return loanRepository.findById(id)
                .map(loanMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));
    }

    @Override
    public Page<LoanResponseDTO> getMyLoans(Pageable pageable) {
        User user = userService.getCurrentUser();
        return loanRepository.findByUserId(user.getId(), pageable)
                .map(loanMapper::toDTO);
    }

    @Override
    public Page<LoanResponseDTO> getAllLoans(Pageable pageable) {
        return loanRepository.findAll(pageable)
                .map(loanMapper::toDTO);
    }

    @Override
    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }

    @Override
    public void updateLoanStatus(Long id, LoanStatus status) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));
        loan.setStatus(status);
        loanRepository.save(loan);
    }

    @Override
    public LoanCriterias checkLoanEligibility(CreateLoanRequestDTO request) {
        List<String> errors = validateLoanEligibility(request);
        if (!errors.isEmpty()) {
            throw new IneligibleForLoanException("Loan request is ineligible", errors);
        }
        return new LoanCriterias(
                request.age(),
                request.monthlyIncome(),
                request.creditScore(),
                request.debtRatio(),
                request.collateralDescription(),
                request.amount(),
                request.termMonths()
        );
    }

    private List<String> validateLoanEligibility(CreateLoanRequestDTO request) {
        List<String> errors = new ArrayList<>();
        if (request.monthlyIncome() < 4000) {
            errors.add("Monthly income must be at least 4000");
        }
        if (request.creditScore() < 600) {
            errors.add("Credit score must be at least 600");
        }
        if (request.debtRatio() > 0.4) {
            errors.add("Debt ratio must be less than 0.4");
        }
        if (request.amount() < 1000) {
            errors.add("Amount must be at least 1000");
        }
        if (request.termMonths() < 6) {
            errors.add("Term in months must be at least 6");
        }
        return errors;
    }
}
