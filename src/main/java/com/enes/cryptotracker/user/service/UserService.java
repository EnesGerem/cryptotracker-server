package com.enes.cryptotracker.user.service;

import com.enes.cryptotracker.user.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User retrieveUser(String username);
    User getCurrentAuthenticatedUser();
}
