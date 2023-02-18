package com.enes.cryptotracker.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "security")
@PropertySource("classpath:security.properties")
@Getter @Setter
public class SecurityProperties {
    private String jwtSecret;
    private String authorizationHeaderPrefix;
    private String usernameParameter;
    private String passwordParameter;
    private String accessToken;
    private String refreshToken;
    private String jwtClaimRoles;
    private Long accessTokenExpirationMillis;
    private Long refreshTokenExpirationMillis;
}
