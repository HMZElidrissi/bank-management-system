package ma.hmzelidrissi.bankmanagementsystem.services;

import ma.hmzelidrissi.bankmanagementsystem.dtos.user.CreateUserRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.user.UpdateUserRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.user.UserResponseDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.user.UserSummaryDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO createUser(CreateUserRequestDTO request);

    UserResponseDTO getUserById(Long id);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO updateUser(Long id, UpdateUserRequestDTO request);

    void deleteUser(Long id);

    UserResponseDTO getCurrentUserProfile();

    List<UserSummaryDTO> getAllCustomers();
}
