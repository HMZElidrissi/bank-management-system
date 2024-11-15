package ma.hmzelidrissi.bankmanagementsystem.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;

import ma.hmzelidrissi.bankmanagementsystem.entities.User;
import ma.hmzelidrissi.bankmanagementsystem.enums.Role;
import org.springframework.data.domain.Pageable;

/**
 * Test class for ma.hmzelidrissi.bankmanagementsystem.repositories.UserRepository.
 *
 * @author hmzelidrissi
 * @version 1.0
 * @see UserRepository
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest extends AbstractTestcontainers {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        user1 = User.builder()
                .name("Hamza El Idrissi")
                .email("hamza@mail.com")
                .password("password123")
                .role(Role.USER)
                .build();

        user2 = User.builder()
                .name("Zakaria El Idrissi")
                .email("zakaria@mail.com")
                .password("password456")
                .role(Role.ADMIN)
                .build();

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.flush();

        System.out.println(applicationContext.getBeanDefinitionCount());
    }

    @Test
    @DisplayName("Should find user by email")
    void shouldFindUserByEmail() {
        Page<User> found = userRepository.searchByEmailOrName("hamza@mail", Pageable.unpaged());

        assertThat(found).hasSize(1)
                .first()
                .satisfies(user -> {
                    assertThat(user.getName()).isEqualTo("Hamza El Idrissi");
                    assertThat(user.getEmail()).isEqualTo("hamza@mail.com");
                });
    }

    @Test
    @DisplayName("Should find user by name")
    void shouldFindUserByName() {
        Page<User> found = userRepository.searchByEmailOrName("Zakaria", Pageable.unpaged());

        assertThat(found).hasSize(1)
                .first()
                .satisfies(user -> {
                    assertThat(user.getName()).isEqualTo("Zakaria El Idrissi");
                    assertThat(user.getEmail()).isEqualTo("zakaria@mail.com");
                });
    }
}