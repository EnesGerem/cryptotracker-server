package com.enes.cryptotracker.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enes.cryptotracker.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
@RequiredArgsConstructor
public class SecurityUtils {

    private final String jwtSecret = "sp7jmCi5L2BZ6jQSo3it0DRi1pcMabKq2PUaEMdfZzBbm8InHx3nDasR3NiC";
    private final SecurityProperties securityProperties;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

    public String createAccessToken(User user, String issuer) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + securityProperties.getAccessTokenExpirationMillis()))
                .withIssuer(issuer)
                .withClaim(securityProperties.getJwtClaimRoles(), user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .sign(algorithm);
    }

    public String createRefreshToken(String username, String issuer) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + securityProperties.getRefreshTokenExpirationMillis()))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public DecodedJWT getDecodedJWT(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public void putHeaders(String accessToken, String refreshToken, HttpServletResponse response) throws IOException {
        Map<String, String> tokens = new HashMap<>();
        tokens.put(securityProperties.getAccessToken(), accessToken);
        tokens.put(securityProperties.getRefreshToken(), refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), tokens);
    }

    public void putErrors(HttpServletResponse response, String message) throws IOException {
        response.setHeader("error", message);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        Map<String, String> error = new HashMap<>();
        error.put("error_message", message);
        response.setContentType(APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), error);
    }
}
