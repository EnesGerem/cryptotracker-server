package com.enes.cryptotracker.alert;

import com.enes.cryptotracker.alert.entity.Alert;
import com.enes.cryptotracker.alert.entity.enums.AlertStatus;
import com.enes.cryptotracker.general.AbstractRepositoryTest;
import com.enes.cryptotracker.general.TestEntityBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlertRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AlertRepository alertRepository;

    @Test
    public void testFindAlertsByStatusIsNew() {
        Alert alert = TestEntityBuilder.generateTestAlert();
        alert.setStatus(AlertStatus.TRIGGERED);
        List<Alert> alerts = List.of(TestEntityBuilder.generateTestAlert(), TestEntityBuilder.generateTestAlert(), alert);
        alerts.forEach(testEntityManager::persist);

        List<Alert> alertsDb = alertRepository.findAlertsByStatusIsNew();

        assertEquals(2, alertsDb.size());
    }
}
