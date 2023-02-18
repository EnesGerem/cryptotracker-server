package com.enes.cryptotracker.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.enes.cryptotracker.user.entity.User;
import com.enes.cryptotracker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class RefreshTokenController {

    private final UserService userService;
    private final SecurityUtils securityUtils;
    private final SecurityProperties securityProperties;

    @GetMapping
    public void getRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(securityProperties.getAuthorizationHeaderPrefix())) {
            try {
                String refreshToken = authorizationHeader.substring(securityProperties.getAuthorizationHeaderPrefix().length());
                DecodedJWT decoded = securityUtils.getDecodedJWT(refreshToken);
                String username = decoded.getSubject();
                User user = userService.retrieveUser(username);
                String issuer = request.getRequestURL().toString();
                String accessToken = securityUtils.createAccessToken(user, issuer);
                securityUtils.putHeaders(accessToken, refreshToken, response);
            } catch (Exception e) {
                securityUtils.putErrors(response, e.getMessage());
            }
        } else throw new RuntimeException("Authorization header's name is not correct");
    }
}
