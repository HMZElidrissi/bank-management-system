package ma.hmzelidrissi.bankmanagementsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.hmzelidrissi.bankmanagementsystem.dtos.PageResponse;
import ma.hmzelidrissi.bankmanagementsystem.dtos.user.CreateUserRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.user.UpdateUserRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.user.UserResponseDTO;
import ma.hmzelidrissi.bankmanagementsystem.services.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "APIs for managing users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Admin endpoints
     */
    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createUser(@Valid @RequestBody CreateUserRequestDTO request) {
        return userService.createUser(request);
    }

    @PutMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update user by ID")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequestDTO request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user by ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping
    // @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all customers")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<UserResponseDTO> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort.Direction direction = Sort.Direction.fromString(sortDir.toLowerCase());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return userService.getAllCustomers(pageRequest);
    }

    @GetMapping("/search")
    // @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Search users")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<UserResponseDTO> searchUsers(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort.Direction direction = Sort.Direction.fromString(sortDir.toLowerCase());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return userService.searchUsers(query, pageRequest);
    }

    /**
     * Employee endpoints
     */
    @GetMapping("/customers")
    // @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @Operation(summary = "Get all customers")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<UserResponseDTO> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort.Direction direction = Sort.Direction.fromString(sortDir.toLowerCase());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return userService.getAllUsers(pageRequest);
    }

    /**
     * Customer endpoints
     */
    @GetMapping("/profile")
    // @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get current user profile")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO getCurrentUserProfile() {
        return userService.getCurrentUserProfile();
    }

    @GetMapping("/{id}")
    // @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @Operation(summary = "Get user by ID")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
