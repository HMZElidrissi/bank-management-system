package ma.hmzelidrissi.bankmanagementsystem.services;

import ma.hmzelidrissi.bankmanagementsystem.dtos.PageResponse;
import ma.hmzelidrissi.bankmanagementsystem.dtos.user.*;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponseDTO createUser(CreateUserRequestDTO request);

    UserResponseDTO getUserById(Long id);

    PageResponse<UserResponseDTO> getAllUsers(Pageable pageable);

    UserResponseDTO updateUser(Long id, UpdateUserRequestDTO request);

    void deleteUser(Long id);

    PageResponse<UserResponseDTO> searchUsers(String query, Pageable pageable);

    UserResponseDTO getCurrentUserProfile();

    PageResponse<UserResponseDTO> getAllCustomers(Pageable pageable);
}