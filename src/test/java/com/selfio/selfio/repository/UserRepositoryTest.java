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
        User expectedUser = new User(email, "12345", false);
        userRepository.save(expectedUser);
        User givenUser = userRepository.findByEmail(email).get();
        assertThat(expectedUser).isEqualTo(givenUser);
    }

    @Test
    void shouldCheckIfExistsByEmail() {
        String email = "andrey@mail.ru";
        User expectedUser = new User(email, "test", true);

        userRepository.save(expectedUser);

        boolean exists = userRepository.existsByEmail(email);
        assertThat(exists).isEqualTo(true);

    }
}