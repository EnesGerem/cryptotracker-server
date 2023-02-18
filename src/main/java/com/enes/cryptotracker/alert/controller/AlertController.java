package com.enes.cryptotracker.alert.controller;

import com.enes.cryptotracker.alert.service.AlertService;
import com.enes.cryptotracker.alert.controller.dto.AlertDTO;
import com.enes.cryptotracker.alert.controller.dto.CreateAlertDTO;
import com.enes.cryptotracker.alert.entity.enums.AlertStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/alert")
public class AlertController {
    private final AlertService alertService;

    @GetMapping("/user/{username}")
    public List<AlertDTO> getUserAlerts(@PathVariable String username) {
        return alertService.retrieveUserAlerts(username).stream().map(AlertDTO::new).toList();
    }

    @GetMapping("/status/{alertId}")
    public AlertStatus getAlertStatus(@PathVariable Long alertId) {
        return alertService.retrieveAlertStatus(alertId);
    }

    @PostMapping("/create")
    public void createAlert(@RequestBody CreateAlertDTO alertDTO) {
        alertService.addAlert(alertDTO.getDomainObject());
    }

    @PutMapping("/cancel/{alertId}")
    public void cancelAlert(@PathVariable Long alertId) {
        alertService.updateAlertStatus(alertId, AlertStatus.CANCELLED);
    }

    @PutMapping("/close/{alertId}")
    public void closeAlert(@PathVariable Long alertId) {
        alertService.updateAlertStatus(alertId, AlertStatus.ACKED);
    }
}
