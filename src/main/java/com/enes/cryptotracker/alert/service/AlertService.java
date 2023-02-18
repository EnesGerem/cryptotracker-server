package com.enes.cryptotracker.alert.service;

import com.enes.cryptotracker.alert.AlertRepository;
import com.enes.cryptotracker.alert.entity.Alert;
import com.enes.cryptotracker.alert.entity.enums.AlertStatus;
import com.enes.cryptotracker.currency.CurrencyService;
import com.enes.cryptotracker.currency.entity.Currency;
import com.enes.cryptotracker.user.entity.User;
import com.enes.cryptotracker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertService {
    private final AlertRepository alertRepository;
    private final UserService userService;
    private final CurrencyService currencyService;

    @Transactional(readOnly = true)
    public Set<Alert> retrieveUserAlerts(String username) {
        User user = userService.retrieveUser(username);
        return user.getAlertSet();
    }

    @Transactional
    public void addAlert(Alert alert) {
        User authUser = userService.getCurrentAuthenticatedUser();
        Currency currency = currencyService.retrieveEnabledCurrencyBySymbol(alert.getCurrency().getSymbol());
        alert.setUser(authUser);
        alert.setCurrency(currency);
        alertRepository.save(alert);
    }

    @Transactional
    public void updateAlertStatus(Long alertId, AlertStatus status) {
        Alert alert = getAuthUserAlertById(alertId);
        AlertStatus oldStatus = alert.getStatus();
        throwExceptionIfAlertIsNotUpdatable(status, alert);

        alert.setStatus(status);
        alertRepository.save(alert);
        log.info("Alert with id {} is updated from status {} to status {}", alertId, oldStatus, status);
    }

    private void throwExceptionIfAlertIsNotUpdatable(AlertStatus status, Alert alert) {
        if(ifAlertCancelIsAlertStatusNotTriggered(alert.getStatus(), status) ||
           ifAlertAcknowledgeIsAlertStatusTriggered(alert.getStatus(), status))
           throw new RuntimeException("Alert cannot be updated");
    }

    private boolean ifAlertCancelIsAlertStatusNotTriggered(AlertStatus oldStatus, AlertStatus newStatus) {
        return newStatus.equals(AlertStatus.CANCELLED) && oldStatus.equals(AlertStatus.TRIGGERED);
    }

    private boolean ifAlertAcknowledgeIsAlertStatusTriggered(AlertStatus oldStatus, AlertStatus newStatus) {
        return newStatus.equals(AlertStatus.ACKED) && !oldStatus.equals(AlertStatus.TRIGGERED);
    }

    @Transactional(readOnly = true)
    public AlertStatus retrieveAlertStatus(Long alertId) {
        return getAuthUserAlertById(alertId).getStatus();
    }

    private Alert getAuthUserAlertById(Long alertId) {
        Alert alert = alertRepository.findById(alertId).orElseThrow(()->new IllegalArgumentException("Alert does not exist"));
        User user = userService.getCurrentAuthenticatedUser();
        throwExceptionIfAlertDoesNotBelongToAuthUser(alert, user);
        return alert;
    }

    private static void throwExceptionIfAlertDoesNotBelongToAuthUser(Alert alert, User user) {
        if (!user.getAlertSet().contains(alert)) {
            log.error("Alert cannot be updated by user: {}", user.getUsername());
            throw new RuntimeException();
        }
    }

    @Transactional
    public void checkAlerts() {
        List<Alert> newAlerts = alertRepository.findAlertsByStatusIsNew();
        newAlerts.forEach(this::checkAlert);
    }

    private void checkAlert(Alert alert) {
        if (alert.getTargetPrice().equals(alert.getCurrency().getCurrentPrice())) {
            alert.setStatus(AlertStatus.TRIGGERED);
            alertRepository.save(alert);
            log.info("{}'s alert for the currency {} has reached to target price of {}", alert.getUser().getUsername(), alert.getCurrency().getSymbol(), alert.getTargetPrice());
        }
    }
}
