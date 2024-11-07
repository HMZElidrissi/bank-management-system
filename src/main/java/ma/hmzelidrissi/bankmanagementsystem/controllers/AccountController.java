package ma.hmzelidrissi.bankmanagementsystem.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.hmzelidrissi.bankmanagementsystem.dtos.PageResponse;
import ma.hmzelidrissi.bankmanagementsystem.dtos.account.AccountResponseDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.account.CreateAccountRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.account.UpdateAccountRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.services.AccountService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    /**
     * Admin endpoints
     */
    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountResponseDTO> createAccount(@Valid @RequestBody CreateAccountRequestDTO request) {
        return ResponseEntity.ok(accountService.createAccount(request));
    }

    @PutMapping("/{id}/status")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountResponseDTO> updateAccountStatus(
            @PathVariable Long id,
            @RequestBody UpdateAccountRequestDTO request) {
        return ResponseEntity.ok(accountService.updateAccountStatus(id, request));
    }

    @PutMapping("/{id}/balance")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountResponseDTO> updateAccountBalance(
            @PathVariable Long id,
            @RequestBody UpdateAccountRequestDTO request) {
        return ResponseEntity.ok(accountService.updateAccountBalance(id, request));
    }

    @DeleteMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Mutual Admin and Employee endpoints
     */

    @GetMapping
    // @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<PageResponse<AccountResponseDTO>> getAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort.Direction direction = Sort.Direction.fromString(sortDir.toLowerCase());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return ResponseEntity.ok(accountService.getAllAccounts(pageRequest));
    }

    @GetMapping("/{userId}")
    // @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<List<AccountResponseDTO>> getAccountsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.getAccountsByUserId(userId));
    }

    /**
     * Customer endpoints
     */
    @GetMapping("/my")
    // @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<AccountResponseDTO>> getMyAccounts() {
        return ResponseEntity.ok(accountService.getMyAccounts());
    }
}
