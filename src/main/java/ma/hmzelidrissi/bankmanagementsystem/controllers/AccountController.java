package ma.hmzelidrissi.bankmanagementsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.hmzelidrissi.bankmanagementsystem.dtos.PageResponse;
import ma.hmzelidrissi.bankmanagementsystem.dtos.account.AccountResponseDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.account.CreateAccountRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.account.UpdateAccountRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.services.AccountService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@Tag(name = "Account Management", description = "APIs for managing accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    /**
     * Admin endpoints
     */
    @PostMapping
    @Operation(summary = "Create a new account")
    @ResponseStatus(HttpStatus.CREATED)
     @PreAuthorize("hasRole('ADMIN')")
    public AccountResponseDTO createAccount(@Valid @RequestBody CreateAccountRequestDTO request) {
        return accountService.createAccount(request);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update account status")
    @ResponseStatus(HttpStatus.OK)
     @PreAuthorize("hasRole('ADMIN')")
    public AccountResponseDTO updateAccountStatus(
            @PathVariable Long id,
            @RequestBody UpdateAccountRequestDTO request) {
        return accountService.updateAccountStatus(id, request);
    }

    @PutMapping("/{id}/balance")
    @Operation(summary = "Update account balance")
    @ResponseStatus(HttpStatus.OK)
     @PreAuthorize("hasRole('ADMIN')")
    public AccountResponseDTO updateAccountBalance(
            @PathVariable Long id,
            @RequestBody UpdateAccountRequestDTO request) {
        return accountService.updateAccountBalance(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete account")
    @ResponseStatus(HttpStatus.NO_CONTENT)
     @PreAuthorize("hasRole('ADMIN')")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }

    /**
     * Mutual Admin and Employee endpoints
     */
    @GetMapping
    @Operation(summary = "Get all accounts")
    @ResponseStatus(HttpStatus.OK)
     @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public PageResponse<AccountResponseDTO> getAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort.Direction direction = Sort.Direction.fromString(sortDir.toLowerCase());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return accountService.getAllAccounts(pageRequest);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get accounts by user ID")
    @ResponseStatus(HttpStatus.OK)
     @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public List<AccountResponseDTO> getAccountsByUserId(@PathVariable Long userId) {
        return accountService.getAccountsByUserId(userId);
    }

    /**
     * Customer endpoints
     */
    @GetMapping("/my")
    @Operation(summary = "Get my accounts")
    @ResponseStatus(HttpStatus.OK)
     @PreAuthorize("hasRole('USER')")
    public List<AccountResponseDTO> getMyAccounts() {
        return accountService.getMyAccounts();
    }
}