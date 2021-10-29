package com.selfio.selfio;

import com.selfio.selfio.repository.UserRepository;
import com.selfio.selfio.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import static org.junit.jupiter.api.Assertions.assertEquals;


//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//@Rollback(false)
public class UserRepositoryTests {

//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private TestEntityManager testEntityManager;
//
//    @Test
//    public void testCreateUser() {
//        User userDataObject = new User();
//        userDataObject.setEmail("georgiy@edu.hse.ru");
//        userDataObject.setPassword("qwerty123");
//        userDataObject.setVerified(true);
//        User savedUser =  userRepository.save(userDataObject);
//        User existingUser = testEntityManager.find(User.class, savedUser.getId());
//        assertEquals(savedUser, existingUser);
//    }
}
