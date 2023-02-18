package com.enes.cryptotracker.currency.controller.dto;

import com.enes.cryptotracker.currency.entity.Currency;
import com.enes.cryptotracker.general.dto.ClientToServerDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateCurrencyDTO implements ClientToServerDTO<Currency> {
    @NotEmpty
    private String symbol;

    @NotEmpty
    private String name;

    @Override
    public Currency getDomainObject() {
        return Currency.builder()
                .enabled(false)
                .symbol(symbol)
                .name(name)
                .build();
    }
}
