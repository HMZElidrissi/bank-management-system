package ma.hmzelidrissi.bankmanagementsystem.services;

import ma.hmzelidrissi.bankmanagementsystem.dtos.PageResponse;
import ma.hmzelidrissi.bankmanagementsystem.dtos.user.CreateUserRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.user.UpdateUserRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.user.UserResponseDTO;
import ma.hmzelidrissi.bankmanagementsystem.entities.User;
import ma.hmzelidrissi.bankmanagementsystem.enums.Role;
import ma.hmzelidrissi.bankmanagementsystem.exceptions.UserAlreadyExistsException;
import ma.hmzelidrissi.bankmanagementsystem.mappers.UserMapper;
import ma.hmzelidrissi.bankmanagementsystem.repositories.UserRepository;
import ma.hmzelidrissi.bankmanagementsystem.services.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserResponseDTO userResponseDTO;
    private CreateUserRequestDTO createUserRequestDTO;
    private UpdateUserRequestDTO updateUserRequestDTO;

    @BeforeEach
    void setUp() {
        // Setup test user
        user = User.builder()
                .id(1L)
                .name("Youssef El Alami")
                .email("youssef.elalami@gmail.com")
                .password("encoded_password")
                .age(30)
                .monthlyIncome(15000.0)
                .creditScore(750)
                .role(Role.USER)
                .build();

        userResponseDTO = UserResponseDTO.builder()
                .id(1L)
                .name("Youssef El Alami")
                .email("youssef.elalami@gmail.com")
                .age(30)
                .monthlyIncome(15000.0)
                .creditScore(750)
                .role(Role.USER)
                .build();

        createUserRequestDTO = new CreateUserRequestDTO(
                "Youssef El Alami",
                "youssef.elalami@gmail.com",
                "password123",
                30,
                15000.0,
                750,
                Role.USER
        );

        updateUserRequestDTO = new UpdateUserRequestDTO(
                "Youssef El Alami",
                31,
                16000.0,
                760
        );
    }

    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUser() {
        // Arrange
        when(userRepository.existsByEmail(createUserRequestDTO.email())).thenReturn(false);
        when(passwordEncoder.encode(createUserRequestDTO.password())).thenReturn("encoded_password");
        when(userMapper.toEntity(createUserRequestDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(userResponseDTO);

        // Act
        UserResponseDTO result = userService.createUser(createUserRequestDTO);

        // Assert
        assertThat(result)
                .satisfies(response -> {
                    assertThat(response.name()).isEqualTo("Youssef El Alami");
                    assertThat(response.email()).isEqualTo("youssef.elalami@gmail.com");
                    assertThat(response.age()).isEqualTo(30);
                    assertThat(response.monthlyIncome()).isEqualTo(15000.0);
                    assertThat(response.creditScore()).isEqualTo(750);
                    assertThat(response.role()).isEqualTo(Role.USER);
                });

        verify(userRepository).existsByEmail(createUserRequestDTO.email());
        verify(passwordEncoder).encode(createUserRequestDTO.password());
        verify(userMapper).toEntity(createUserRequestDTO);
        verify(userRepository).save(user);
        verify(userMapper).toResponse(user);
    }

    @Test
    @DisplayName("Should throw exception when creating user with existing email")
    void shouldThrowExceptionWhenCreatingUserWithExistingEmail() {
        // Arrange
        String existingEmail = "karim.ziyech@gmail.com";
        CreateUserRequestDTO requestWithExistingEmail = new CreateUserRequestDTO(
                "Karim Ziyech",
                existingEmail,
                "password123",
                28,
                20000.0,
                800,
                Role.USER
        );

        when(userRepository.existsByEmail(existingEmail)).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> userService.createUser(requestWithExistingEmail))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("Email already exists");
    }

    @Test
    @DisplayName("Should get user by id")
    void shouldGetUserById() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(userResponseDTO);

        // Act
        UserResponseDTO result = userService.getUserById(1L);

        // Assert
        assertThat(result)
                .satisfies(response -> {
                    assertThat(response.name()).isEqualTo("Youssef El Alami");
                    assertThat(response.monthlyIncome()).isEqualTo(15000.0);
                    assertThat(response.creditScore()).isEqualTo(750);
                });
    }

    @Test
    @DisplayName("Should get all users with pagination")
    void shouldGetAllUsersWithPagination() {
        // Arrange
        User user2 = User.builder()
                .id(2L)
                .name("Achraf Hakimi")
                .email("achraf.hakimi@gmail.com")
                .age(25)
                .monthlyIncome(25000.0)
                .creditScore(820)
                .role(Role.USER)
                .build();

        UserResponseDTO response2 = UserResponseDTO.builder()
                .id(2L)
                .name("Achraf Hakimi")
                .email("achraf.hakimi@gmail.com")
                .age(25)
                .monthlyIncome(25000.0)
                .creditScore(820)
                .role(Role.USER)
                .build();

        Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = new PageImpl<>(List.of(user, user2), pageable, 2);

        when(userRepository.findAll(pageable)).thenReturn(userPage);
        when(userMapper.toResponse(user)).thenReturn(userResponseDTO);
        when(userMapper.toResponse(user2)).thenReturn(response2);

        // Act
        PageResponse<UserResponseDTO> result = userService.getAllUsers(pageable);

        // Assert
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent().get(0).name()).isEqualTo("Youssef El Alami");
        assertThat(result.getContent().get(1).name()).isEqualTo("Achraf Hakimi");
    }

    @Test
    @DisplayName("Should update user successfully")
    void shouldUpdateUser() {
        // Arrange
        User updatedUser = User.builder()
                .id(1L)
                .name("Youssef El Alami")
                .email("youssef.elalami@gmail.com")
                .age(31)
                .monthlyIncome(16000.0)
                .creditScore(760)
                .role(Role.USER)
                .build();

        UserResponseDTO updatedResponse = UserResponseDTO.builder()
                .id(1L)
                .name("Youssef El Alami")
                .email("youssef.elalami@gmail.com")
                .age(31)
                .monthlyIncome(16000.0)
                .creditScore(760)
                .role(Role.USER)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userMapper).updateEntity(user, updateUserRequestDTO);
        when(userRepository.save(user)).thenReturn(updatedUser);
        when(userMapper.toResponse(updatedUser)).thenReturn(updatedResponse);

        // Act
        UserResponseDTO result = userService.updateUser(1L, updateUserRequestDTO);

        // Assert
        assertThat(result)
                .satisfies(response -> {
                    assertThat(response.age()).isEqualTo(31);
                    assertThat(response.monthlyIncome()).isEqualTo(16000.0);
                    assertThat(response.creditScore()).isEqualTo(760);
                });
    }

    @Test
    @DisplayName("Should search users by name or email")
    void shouldSearchUsers() {
        // Arrange
        User user2 = User.builder()
                .id(2L)
                .name("Soufiane Amrabat")
                .email("soufiane.amrabat@gmail.com")
                .age(27)
                .monthlyIncome(18000.0)
                .creditScore(780)
                .role(Role.USER)
                .build();

        UserResponseDTO response2 = UserResponseDTO.builder()
                .id(2L)
                .name("Soufiane Amrabat")
                .email("soufiane.amrabat@gmail.com")
                .age(27)
                .monthlyIncome(18000.0)
                .creditScore(780)
                .role(Role.USER)
                .build();

        String searchQuery = "soufiane";
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = new PageImpl<>(List.of(user2), pageable, 1);

        when(userRepository.searchByEmailOrName(searchQuery, pageable)).thenReturn(userPage);
        when(userMapper.toResponse(user2)).thenReturn(response2);

        // Act
        PageResponse<UserResponseDTO> result = userService.searchUsers(searchQuery, pageable);

        // Assert
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).name()).isEqualTo("Soufiane Amrabat");
        assertThat(result.getContent().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should get current user profile")
    void shouldGetCurrentUserProfile() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("youssef.elalami@gmail.com");
        when(userRepository.findByEmail("youssef.elalami@gmail.com")).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(userResponseDTO);

        // Act
        UserResponseDTO result = userService.getCurrentUserProfile();

        // Assert
        assertThat(result)
                .satisfies(response -> {
                    assertThat(response.name()).isEqualTo("Youssef El Alami");
                    assertThat(response.email()).isEqualTo("youssef.elalami@gmail.com");
                    assertThat(response.creditScore()).isEqualTo(750);
                });
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }
}