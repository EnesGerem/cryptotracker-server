package com.enes.cryptotracker.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;


@AllArgsConstructor @Getter
public enum UserRole {
    ADMIN(Set.of(UserPermission.CURRENCY_WRITE, UserPermission.CURRENCY_READ, UserPermission.ALERT_WRITE, UserPermission.ALERT_READ)),
    USER(Set.of(UserPermission.CURRENCY_READ, UserPermission.ALERT_WRITE, UserPermission.ALERT_READ));

    private final Set<UserPermission> permissions;

    public Set<GrantedAuthority> getGrantedAuthorities() {
        Set<GrantedAuthority> permissions = getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return permissions;
    }
}
