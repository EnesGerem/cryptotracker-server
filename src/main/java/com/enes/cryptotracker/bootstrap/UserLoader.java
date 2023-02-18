package com.enes.cryptotracker.bootstrap;

import com.enes.cryptotracker.user.UserRepository;
import com.enes.cryptotracker.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class UserLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        List<User> users = List.of(userRepository.findById(10001L).get(), userRepository.findById(10002L).get(), userRepository.findById(10003L).get());
        users.forEach(user -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        });
    }
}
