package com.enes.cryptotracker.currency;

import com.enes.cryptotracker.currency.api.FakeCurrencyPriceApi;
import com.enes.cryptotracker.currency.entity.Currency;
import com.enes.cryptotracker.error.exception.UnsupportedCurrencyCreationException;
import com.enes.cryptotracker.general.parameter.ParameterService;
import com.enes.cryptotracker.general.parameter.entity.enums.ParameterKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final FakeCurrencyPriceApi fakeCurrencyPriceApi;
    private final ParameterService parameterService;

    @Transactional(readOnly = true)
    public Currency retrieveEnabledCurrencyBySymbol(String symbol) {
        return currencyRepository.findCurrencyBySymbolAndEnabledIsTrue(symbol).orElseThrow(()->new IllegalArgumentException("Currency does not exist or not enabled"));
    }

    @Transactional
    public void enableCurrency(String symbol) {
        Currency currency = currencyRepository.findCurrencyBySymbolAndEnabledIsFalse(symbol).orElseThrow(()->new IllegalArgumentException("Currency does not exist or already enabled"));
        currency.setEnabled(true);
        currencyRepository.save(currency);
    }

    @Transactional(readOnly = true)
    public List<Currency> retrieveEnabledCurrencies() {
        return currencyRepository.findCurrenciesByEnabledIsTrue().orElseThrow(()->new IllegalArgumentException("error"));
    }

    @Transactional
    public void updateCurrentPrices() {
        List<Currency> currencies = currencyRepository.findAll();
        List<Currency> updatedCurrencies = fakeCurrencyPriceApi.fetchCurrencyPrices(currencies);
        currencyRepository.saveAll(updatedCurrencies);
    }

    @Transactional
    public void addCurrency(Currency currency) {
        if (isCurrencyUnsupported(currency.getSymbol())) throw new UnsupportedCurrencyCreationException(String.format("%s is not supported", currency.getSymbol()));
        Currency currencyWithPrice = fakeCurrencyPriceApi.fetchCurrencyPrice(currency);
        currencyRepository.save(currencyWithPrice);
    }

    private boolean isCurrencyUnsupported(String symbol) {
        List<String> unsupportedCurrencies = parameterService.retrieveParameterValueListByKey(ParameterKey.UNSUPPORTED_CURRENCIES);
        return unsupportedCurrencies.contains(symbol);
    }
}
