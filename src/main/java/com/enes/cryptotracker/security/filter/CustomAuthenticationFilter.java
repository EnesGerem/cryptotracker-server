package com.enes.cryptotracker.security.filter;

import com.enes.cryptotracker.security.SecurityProperties;
import com.enes.cryptotracker.security.SecurityUtils;
import com.enes.cryptotracker.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final SecurityUtils securityUtils;
    private final SecurityProperties securityProperties;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter(securityProperties.getUsernameParameter());
        String password = request.getParameter(securityProperties.getPasswordParameter());
        log.info("User {} attempted to authenticate with password: {}", username, password);

        var authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        log.info("User {} is successfully authenticated", user.getUsername());

        String issuer = request.getRequestURL().toString();
        String accessToken = securityUtils.createAccessToken(user, issuer);
        String refreshToken = securityUtils.createRefreshToken(user.getUsername(), issuer);
        securityUtils.putHeaders(accessToken, refreshToken, response);
    }
}
