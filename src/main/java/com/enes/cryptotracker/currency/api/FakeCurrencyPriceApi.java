package com.enes.cryptotracker.currency.api;

import com.enes.cryptotracker.currency.entity.Currency;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FakeCurrencyPriceApi {

    public Currency fetchCurrencyPrice(Currency currency) {
        currency.setCurrentPrice(randomPrice(1L,10000L));
        return currency;
    }

    public List<Currency> fetchCurrencyPrices(List<Currency> currencies) {
        currencies.forEach(currency -> currency.setCurrentPrice(randomPrice(currency.getCurrentPrice().longValue()-50,currency.getCurrentPrice().longValue()+50)));
        return currencies;
    }

    private BigDecimal randomPrice(long leftLimit, long rightLimit) {
        long randomLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        return BigDecimal.valueOf(randomLong);
    }
}
