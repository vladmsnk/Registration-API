package com.selfio.selfio.repository;

import com.selfio.selfio.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    public UserRepository userRepository;


    @Test
    void findByEmail() {
        String email = "vlad@mail.ru";
        User expectedUser = createUser("vlad", email, "12345", true);

        userRepository.save(expectedUser);

        User givenUser = userRepository.findByEmail(email).get();

        assertThat(expectedUser).isEqualTo(givenUser);
    }

    @Test
    void findByLogin() {
        String login = "vladmsnk";
        User expectedUser = createUser(login, "vladmsnk@mail.ru", "hello", false);

        userRepository.save(expectedUser);

        User givenUser = userRepository.findByLogin(login);

        assertThat(expectedUser).isEqualTo(givenUser);

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