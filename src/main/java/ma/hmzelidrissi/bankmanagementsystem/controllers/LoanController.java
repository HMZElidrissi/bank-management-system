package ma.hmzelidrissi.bankmanagementsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.hmzelidrissi.bankmanagementsystem.dtos.loan.CreateLoanRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.loan.LoanResponseDTO;
import ma.hmzelidrissi.bankmanagementsystem.enums.LoanStatus;
import ma.hmzelidrissi.bankmanagementsystem.services.LoanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/loans")
@Tag(name = "Loans Management", description = "APIs for managing loans")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @PostMapping
    @Operation(summary = "Apply for a new loan")
    // @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponseDTO applyForLoan(
            @Valid @RequestBody CreateLoanRequestDTO request) {
        return loanService.applyForLoan(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get loan by ID")
    // @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    public LoanResponseDTO getLoanById(@PathVariable Long id) {
        return loanService.getLoanById(id);
    }

    @GetMapping("/my")
    @Operation(summary = "Get my loans")
    // @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public Page<LoanResponseDTO> getMyLoans(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortDir.toLowerCase());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return loanService.getMyLoans(pageRequest);
    }

    @GetMapping
    @Operation(summary = "Get all loans")
    // @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    public Page<LoanResponseDTO> getAllLoans(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortDir.toLowerCase());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return loanService.getAllLoans(pageRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete loan by ID")
    // @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
    }

    @PostMapping("/{id}/status")
    @Operation(summary = "Update loan status")
    // @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    public void updateLoanStatus(
            @PathVariable Long id,
            @RequestParam LoanStatus status
    ) {
        loanService.updateLoanStatus(id, status);
    }
}
