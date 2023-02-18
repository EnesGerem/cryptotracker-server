package com.enes.cryptotracker.alert.controller.dto;

import com.enes.cryptotracker.alert.entity.Alert;
import com.enes.cryptotracker.alert.entity.enums.AlertStatus;
import com.enes.cryptotracker.general.dto.ClientToServerDTO;
import lombok.Data;

@Data
public class UpdateAlertStatusDTO implements ClientToServerDTO<Alert> {
    private Long alertId;
    private AlertStatus status;

    @Override
    public Alert getDomainObject() {
        Alert alert = new Alert();
        alert.setId(alertId);
        alert.setStatus(status);
        return alert;
    }
}
