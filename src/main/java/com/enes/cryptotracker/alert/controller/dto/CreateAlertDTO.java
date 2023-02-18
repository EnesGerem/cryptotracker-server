package com.enes.cryptotracker.alert.controller.dto;

import com.enes.cryptotracker.alert.entity.Alert;
import com.enes.cryptotracker.alert.entity.enums.AlertStatus;
import com.enes.cryptotracker.currency.entity.Currency;
import com.enes.cryptotracker.general.dto.ClientToServerDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAlertDTO implements ClientToServerDTO<Alert> {
    private String currencySymbol;
    private Long targetPrice;

    @Override
    public Alert getDomainObject() {
        Currency currency = Currency.builder().symbol(currencySymbol).build();

        return Alert.builder()
                .currency(currency)
                .status(AlertStatus.NEW)
                .targetPrice(BigDecimal.valueOf(targetPrice))
                .build();
    }
}
