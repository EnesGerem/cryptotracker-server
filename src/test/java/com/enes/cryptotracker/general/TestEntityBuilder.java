package com.enes.cryptotracker.general;

import com.enes.cryptotracker.alert.entity.Alert;
import com.enes.cryptotracker.alert.entity.enums.AlertStatus;
import com.enes.cryptotracker.currency.entity.Currency;
import com.enes.cryptotracker.user.entity.User;
import com.enes.cryptotracker.user.entity.UserRole;

import java.math.BigDecimal;

public class TestEntityBuilder {


    public static User generateTestUser() {
        return User.builder()
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .isAccountNonExpired(true)
                .userRole(UserRole.USER)
                .username(generateRandomString())
                .password(generateRandomString())
                .build();
    }

    public static Alert generateTestAlert() {
        return Alert.builder()
                .status(AlertStatus.NEW)
                .targetPrice(generateRandomPrice())
                .build();
    }

    public static Currency generateTestCurrency() {
        return Currency.builder()
                .name(generateRandomString())
                .symbol(generateRandomString())
                .currentPrice(generateRandomPrice())
                .enabled(true)
                .build();
    }

    public static String generateRandomString() {
        return "A" + generateRandomLong();
    }

    public static BigDecimal generateRandomPrice() {
        long leftLimit = 1L;
        long rightLimit = 100000L;
        long randomLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        return BigDecimal.valueOf(randomLong);
    }

    public static Long generateRandomLong() {
        long leftLimit = 1L;
        long rightLimit = 10000000L;
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }
}
