package com.enes.cryptotracker.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.enes.cryptotracker.security.SecurityProperties;
import com.enes.cryptotracker.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final SecurityUtils securityUtils;
    private final SecurityProperties securityProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if (servletPath.equals("/login") || servletPath.equals("/token"))
            filterChain.doFilter(request, response);
        else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith(securityProperties.getAuthorizationHeaderPrefix()))
                try {
                    String token = authorizationHeader.substring(securityProperties.getAuthorizationHeaderPrefix().length()+1);
                    DecodedJWT decoded = securityUtils.getDecodedJWT(token);
                    String username = decoded.getSubject();
                    String[] roles = decoded.getClaim(securityProperties.getJwtClaimRoles()).asArray(String.class);
                    Collection<GrantedAuthority> authorities = Arrays.stream(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                    var authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    log.error("Error logging in: {}",e.getMessage());
                    securityUtils.putErrors(response, e.getMessage());
                }
            else filterChain.doFilter(request, response);
        }
    }
}
