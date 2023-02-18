package com.enes.cryptotracker.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum UserPermission {
    CURRENCY_WRITE("currency:write"),
    CURRENCY_READ("currency:read"),
    ALERT_WRITE("alert:write"),
    ALERT_READ("alert:read");

    private final String permission;
}
