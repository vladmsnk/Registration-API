package com.selfio.selfio.repository;

import com.selfio.selfio.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.*;



@DataJpaTest
class UserRepositoryTest {

    @Autowired
    public UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }


    @Test
    void shouldFindByEmail() {
        String email = "vlad@mail.ru";
        User expectedUser = createUser("vlad", email, "12345", true);

        userRepository.save(expectedUser);

        User givenUser = userRepository.findByEmail(email).get();

        assertThat(expectedUser).isEqualTo(givenUser);
    }

    @Test
    void shouldFindByLogin() {
        String login = "vladmsnk";
        User expectedUser = createUser(login, "vladmsnk@mail.ru", "hello", false);

        userRepository.save(expectedUser);

        User givenUser = userRepository.findByLogin(login);

        assertThat(expectedUser).isEqualTo(givenUser);
    }

    @Test
    void shouldCheckIfExistsByEmail() {
        String login = "vladmsnk";

        User expectedUser = createUser(login, "vladmsnk@mail.ru", "12343", true);

        userRepository.save(expectedUser);

        boolean exists = userRepository.existsByEmail("vladmsnk@mail.ru");
        assertThat(exists).isEqualTo(true);

    }

    private User createUser(String login, String email, String password, boolean isVerified) {
        return new User(
                login,
                email,
                password,
                isVerified
        );
    }
}