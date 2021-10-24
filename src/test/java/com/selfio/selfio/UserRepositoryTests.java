package com.selfio.selfio;

import com.selfio.selfio.repository.UserRepository;
import com.selfio.selfio.entities.UserDataObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import static org.junit.jupiter.api.Assertions.assertEquals;



@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testCreateUser() {
        UserDataObject userDataObject = new UserDataObject();
        userDataObject.setEmail("georgiy@edu.hse.ru");
        userDataObject.setPassword("qwerty123");
        userDataObject.setVerified(true);
        UserDataObject savedUser =  userRepository.save(userDataObject);
        UserDataObject existingUser = testEntityManager.find(UserDataObject.class, savedUser.getId());
        assertEquals(savedUser, existingUser);
    }
}
