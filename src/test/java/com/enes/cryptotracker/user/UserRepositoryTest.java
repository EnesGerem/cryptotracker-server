package com.enes.cryptotracker.user;

import com.enes.cryptotracker.general.AbstractRepositoryTest;
import com.enes.cryptotracker.general.TestEntityBuilder;
import com.enes.cryptotracker.user.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindUserByUsername() {
        User user = TestEntityBuilder.generateTestUser();
        testEntityManager.persist(user);

        Optional<User> userOptional = userRepository.findUserByUsername(user.getUsername());

        assertTrue(userOptional.isPresent());
        assertEquals(user.getUserRole(), userOptional.get().getUserRole());
    }

    @Test
    public void testSaveUser() {
        User user = TestEntityBuilder.generateTestUser();

        userRepository.save(user);

        User userTestDb = testEntityManager.find(User.class, user.getId());
        assertNotNull(userTestDb);
    }


}