package ma.hmzelidrissi.bankmanagementsystem.services;

import ma.hmzelidrissi.bankmanagementsystem.dtos.PageResponse;
import ma.hmzelidrissi.bankmanagementsystem.dtos.account.AccountResponseDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.account.CreateAccountRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.account.UpdateAccountRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.entities.Account;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    PageResponse<AccountResponseDTO> getAllAccounts(Pageable pageable);

    AccountResponseDTO createAccount(CreateAccountRequestDTO request);

    AccountResponseDTO updateAccountStatus(Long id, UpdateAccountRequestDTO request);

    AccountResponseDTO updateAccountBalance(Long id, UpdateAccountRequestDTO request);

    void deleteAccount(Long id);

    List<AccountResponseDTO> getAccountsByUserId(Long userId);

    List<AccountResponseDTO> getMyAccounts();

    Account getAccountById(Long id);
}
