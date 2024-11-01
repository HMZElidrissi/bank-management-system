package ma.hmzelidrissi.bankmanagementsystem.services.impl;

import lombok.RequiredArgsConstructor;
import ma.hmzelidrissi.bankmanagementsystem.dtos.PageResponse;
import ma.hmzelidrissi.bankmanagementsystem.dtos.user.*;
import ma.hmzelidrissi.bankmanagementsystem.entities.User;
import ma.hmzelidrissi.bankmanagementsystem.enums.Role;
import ma.hmzelidrissi.bankmanagementsystem.exceptions.ResourceNotFoundException;
import ma.hmzelidrissi.bankmanagementsystem.exceptions.UserAlreadyExistsException;
import ma.hmzelidrissi.bankmanagementsystem.mappers.UserMapper;
import ma.hmzelidrissi.bankmanagementsystem.repositories.UserRepository;
import ma.hmzelidrissi.bankmanagementsystem.services.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO createUser(CreateUserRequestDTO request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        // User currentUser = getCurrentUser();
        User requestedUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

//        if (currentUser.getRole() == Role.USER && !currentUser.getId().equals(id)) {
//            throw new AccessDeniedException("Cannot access other user's information");
//        }

        return userMapper.toResponse(requestedUser);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "users", key = "#pageable")
    public PageResponse<UserResponseDTO> getAllUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return PageResponse.of(
                userPage.getContent().stream()
                        .map(userMapper::toResponse)
                        .toList(),
                userPage
        );
    }

    @Override
    public UserResponseDTO updateUser(Long id, UpdateUserRequestDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userMapper.updateUserFromRequest(request, user);
        User updatedUser = userRepository.save(user);
        return userMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getCurrentUserProfile() {
        return userMapper.toResponse(getCurrentUser());
    }


    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "customers", key = "#pageable")
    public PageResponse<UserResponseDTO> getAllCustomers(Pageable pageable) {
        Page<User> customerPage = userRepository.findAllByRole(Role.USER, pageable);
        return PageResponse.of(
                customerPage.getContent().stream()
                        .map(userMapper::toResponse)
                        .toList(),
                customerPage
        );
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}