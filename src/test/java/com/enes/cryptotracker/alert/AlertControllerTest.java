package com.enes.cryptotracker.alert;

import com.enes.cryptotracker.alert.controller.AlertController;
import com.enes.cryptotracker.alert.controller.dto.AlertDTO;
import com.enes.cryptotracker.alert.controller.dto.CreateAlertDTO;
import com.enes.cryptotracker.alert.entity.Alert;
import com.enes.cryptotracker.alert.entity.enums.AlertStatus;
import com.enes.cryptotracker.alert.service.AlertService;
import com.enes.cryptotracker.general.AbstractUnitTest;
import com.enes.cryptotracker.general.TestEntityBuilder;
import com.enes.cryptotracker.user.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlertControllerTest extends AbstractUnitTest {
    @InjectMocks
    private AlertController alertController;

    @Mock
    private AlertService alertService;

    @Captor
    private final ArgumentCaptor<Alert> alertArgumentCaptor = ArgumentCaptor.forClass(Alert.class);

    @Test
    public void testGetUserAlerts() {
        User user = TestEntityBuilder.generateTestUser();
        Alert alert = TestEntityBuilder.generateTestAlert();
        alert.setCurrency(TestEntityBuilder.generateTestCurrency());
        user.setAlertSet(Collections.singleton(alert));
        Mockito.when(alertService.retrieveUserAlerts(user.getUsername())).thenReturn(user.getAlertSet());

        List<AlertDTO> userAlerts = alertController.getUserAlerts(user.getUsername());

        Mockito.verify(alertService).retrieveUserAlerts(user.getUsername());
        assertEquals(user.getAlertSet().iterator().next().getId(), userAlerts.iterator().next().getAlertId());
    }

    @Test
    public void testGetAlertStatus() {
        Alert alert = TestEntityBuilder.generateTestAlert();
        alert.setId(1L);
        Mockito.when(alertService.retrieveAlertStatus(alert.getId())).thenReturn(alert.getStatus());

        AlertStatus alertStatus = alertController.getAlertStatus(alert.getId());

        Mockito.verify(alertService).retrieveAlertStatus(alert.getId());
        assertEquals(AlertStatus.NEW, alertStatus);

    }

    @Test
    public void testCreateAlert() {
        Alert alert = TestEntityBuilder.generateTestAlert();
        alert.setCurrency(TestEntityBuilder.generateTestCurrency());
        CreateAlertDTO createAlertDTO = new CreateAlertDTO();
        createAlertDTO.setCurrencySymbol(alert.getCurrency().getSymbol());
        createAlertDTO.setTargetPrice(TestEntityBuilder.generateRandomLong());

        alertController.createAlert(createAlertDTO);

        Mockito.verify(alertService).addAlert(alertArgumentCaptor.capture());
        Alert captured = alertArgumentCaptor.getValue();
        Alert alertAdded = createAlertDTO.getDomainObject();

        assertEquals(alertAdded.getTargetPrice(), captured.getTargetPrice());
        assertEquals(alertAdded.getStatus(), captured.getStatus());
    }

    @Test
    public void testCancelAlert() {
        alertController.cancelAlert(1L);
        Mockito.verify(alertService).updateAlertStatus(1L, AlertStatus.CANCELLED);
    }

    @Test
    public void testCloseAlert() {
        alertController.closeAlert(1L);
        Mockito.verify(alertService).updateAlertStatus(1L, AlertStatus.ACKED);
    }
}
