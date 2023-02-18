package com.enes.cryptotracker.alert.controller.dto;

import com.enes.cryptotracker.alert.entity.Alert;
import com.enes.cryptotracker.alert.entity.enums.AlertStatus;
import com.enes.cryptotracker.currency.controller.dto.CurrencyDTO;
import lombok.Data;

@Data
public class AlertDTO {
    private Long alertId;
    private Long targetPrice;
    private AlertStatus status;
    private CurrencyDTO currencyDTO;

    public AlertDTO(Alert alert) {
        targetPrice = alert.getTargetPrice().longValue();
        status = alert.getStatus();
        alertId = alert.getId();
        currencyDTO = new CurrencyDTO(alert.getCurrency());
    }
}
