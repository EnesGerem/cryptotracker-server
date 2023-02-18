package com.enes.cryptotracker.alert;

import com.enes.cryptotracker.alert.entity.Alert;
import com.enes.cryptotracker.alert.entity.enums.AlertStatus;
import com.enes.cryptotracker.alert.service.AlertService;
import com.enes.cryptotracker.currency.CurrencyService;
import com.enes.cryptotracker.currency.entity.Currency;
import com.enes.cryptotracker.general.AbstractUnitTest;
import com.enes.cryptotracker.general.TestEntityBuilder;
import com.enes.cryptotracker.user.entity.User;
import com.enes.cryptotracker.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlertServiceTest extends AbstractUnitTest {

    @InjectMocks
    private AlertService alertService;

    @Mock
    private AlertRepository alertRepository;

    @Mock
    private UserService userService;

    @Mock
    private CurrencyService currencyService;

    @Captor
    private ArgumentCaptor<Alert> alertArgumentCaptor = ArgumentCaptor.forClass(Alert.class);

    @Test
    public void testRetrieveUserAlerts() {
        User user = TestEntityBuilder.generateTestUser();
        user.setAlertSet(Collections.singleton(TestEntityBuilder.generateTestAlert()));

        Mockito.when(userService.retrieveUser(user.getUsername())).thenReturn(user);

        Set<Alert> alerts = alertService.retrieveUserAlerts(user.getUsername());

        Mockito.verify(userService).retrieveUser(user.getUsername());

        assertEquals(user.getAlertSet(), alerts);
    }

    @Test
    public void testAddAlert() {
        User user = TestEntityBuilder.generateTestUser();
        Alert alert = TestEntityBuilder.generateTestAlert();
        Currency currency = TestEntityBuilder.generateTestCurrency();
        alert.setCurrency(currency);
        user.setAlertSet(Collections.singleton(alert));

        Mockito.when(userService.getCurrentAuthenticatedUser()).thenReturn(user);
        Mockito.when(currencyService.retrieveEnabledCurrencyBySymbol(alert.getCurrency().getSymbol())).thenReturn(currency);

        alertService.addAlert(alert);

        Mockito.verify(alertRepository).save(alertArgumentCaptor.capture());
        Mockito.verify(currencyService).retrieveEnabledCurrencyBySymbol(alert.getCurrency().getSymbol());
        Mockito.verify(userService).getCurrentAuthenticatedUser();

        Alert captured = alertArgumentCaptor.getValue();
        assertEquals(user, captured.getUser());
        assertEquals(currency, captured.getCurrency());
    }

    @Test
    public void testUpdateAlertStatus() {
        User user = TestEntityBuilder.generateTestUser();
        Alert alert = TestEntityBuilder.generateTestAlert();
        alert.setId(1L);
        user.setAlertSet(Collections.singleton(alert));

        Mockito.when(alertRepository.findById(alert.getId())).thenReturn(Optional.of(alert));
        Mockito.when(userService.getCurrentAuthenticatedUser()).thenReturn(user);

        alertService.updateAlertStatus(alert.getId(), AlertStatus.CANCELLED);

        Mockito.verify(alertRepository).save(alertArgumentCaptor.capture());
        Mockito.verify(userService).getCurrentAuthenticatedUser();

        Alert alertSaved = alertArgumentCaptor.getValue();
        assertEquals(AlertStatus.CANCELLED, alertSaved.getStatus());
    }

    @Test
    public void testRetrieveAlertStatus() {
        User user = TestEntityBuilder.generateTestUser();
        Alert alert = TestEntityBuilder.generateTestAlert();
        alert.setId(1L);
        user.setAlertSet(Collections.singleton(alert));

        Mockito.when(alertRepository.findById(alert.getId())).thenReturn(Optional.of(alert));
        Mockito.when(userService.getCurrentAuthenticatedUser()).thenReturn(user);

        AlertStatus status = alertService.retrieveAlertStatus(alert.getId());

        Mockito.verify(alertRepository).findById(alert.getId());
        Mockito.verify(userService).getCurrentAuthenticatedUser();

        assertEquals(alert.getStatus(), status);
    }

    @Test
    public void testCheckAlerts() {
        User user = TestEntityBuilder.generateTestUser();
        Alert alert = TestEntityBuilder.generateTestAlert();
        Currency currency = TestEntityBuilder.generateTestCurrency();
        currency.setCurrentPrice(alert.getTargetPrice());
        alert.setCurrency(currency);
        alert.setUser(user);

        Mockito.when(alertRepository.findAlertsByStatusIsNew()).thenReturn(Collections.singletonList(alert));

        alertService.checkAlerts();

        Mockito.verify(alertRepository).findAlertsByStatusIsNew();
        Mockito.verify(alertRepository).save(alertArgumentCaptor.capture());

        assertEquals(AlertStatus.TRIGGERED, alertArgumentCaptor.getValue().getStatus());
    }

}
