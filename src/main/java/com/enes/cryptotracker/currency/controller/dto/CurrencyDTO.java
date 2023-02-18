package com.enes.cryptotracker.currency.controller.dto;

import com.enes.cryptotracker.currency.entity.Currency;
import lombok.Data;

@Data
public class CurrencyDTO {
    private String name;
    private String symbol;
    private Long currentPrice;

    public CurrencyDTO(Currency currency) {
        name = currency.getName();
        symbol = currency.getSymbol();
        currentPrice = currency.getCurrentPrice().longValue();
    }
}
