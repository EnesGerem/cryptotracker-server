package com.enes.cryptotracker.user;

import com.enes.cryptotracker.general.AbstractUnitTest;
import com.enes.cryptotracker.general.TestEntityBuilder;
import com.enes.cryptotracker.user.entity.User;
import com.enes.cryptotracker.user.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTest extends AbstractUnitTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testLoadUserByUsername() {
        User user = TestEntityBuilder.generateTestUser();

        Mockito.when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());

        Mockito.verify(userRepository).findUserByUsername(user.getUsername());
        assertEquals(user.getUsername(), userDetails.getUsername());
    }

    @Test
    public void testRetrieveUser() {
        User user = TestEntityBuilder.generateTestUser();

        Mockito.when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

        User userDb = userService.retrieveUser(user.getUsername());

        Mockito.verify(userRepository).findUserByUsername(user.getUsername());
        assertEquals(user.getUsername(), userDb.getUsername());
    }
}
