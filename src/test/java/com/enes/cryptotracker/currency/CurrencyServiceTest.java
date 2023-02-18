package com.enes.cryptotracker.currency;

import com.enes.cryptotracker.currency.api.FakeCurrencyPriceApi;
import com.enes.cryptotracker.currency.entity.Currency;
import com.enes.cryptotracker.general.AbstractUnitTest;
import com.enes.cryptotracker.general.TestEntityBuilder;
import com.enes.cryptotracker.general.parameter.ParameterService;
import com.enes.cryptotracker.general.parameter.entity.enums.ParameterKey;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CurrencyServiceTest extends AbstractUnitTest {

    @InjectMocks
    private CurrencyService currencyService;

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private ParameterService parameterService;

    @Mock
    private FakeCurrencyPriceApi fakeCurrencyPriceApi;

    @Captor
    private final ArgumentCaptor<Currency> currencyArgumentCaptor = ArgumentCaptor.forClass(Currency.class);

    @Test
    public void testRetrieveEnabledCurrencyBySymbol() {
        Currency currency = TestEntityBuilder.generateTestCurrency();
        Mockito.when(currencyRepository.findCurrencyBySymbolAndEnabledIsTrue(currency.getSymbol())).thenReturn(Optional.of(currency));

        Currency currencyDb = currencyService.retrieveEnabledCurrencyBySymbol(currency.getSymbol());

        Mockito.verify(currencyRepository).findCurrencyBySymbolAndEnabledIsTrue(currency.getSymbol());
        assertEquals(currency.getSymbol(), currencyDb.getSymbol());
    }

    @Test
    public void testEnableCurrency() {
        Currency currency = TestEntityBuilder.generateTestCurrency();
        currency.setEnabled(false);

        Mockito.when(currencyRepository.findCurrencyBySymbolAndEnabledIsFalse(currency.getSymbol())).thenReturn(Optional.of(currency));

        currencyService.enableCurrency(currency.getSymbol());

        Mockito.verify(currencyRepository).findCurrencyBySymbolAndEnabledIsFalse(currency.getSymbol());
        Mockito.verify(currencyRepository).save(currencyArgumentCaptor.capture());
        assertTrue(currencyArgumentCaptor.getValue().getEnabled());
    }

    @Test
    public void testRetrieveEnabledCurrencies() {
        List<Currency> currencies = List.of(TestEntityBuilder.generateTestCurrency(), TestEntityBuilder.generateTestCurrency());

        Mockito.when(currencyRepository.findCurrenciesByEnabledIsTrue()).thenReturn(Optional.of(currencies));

        List<Currency> currenciesDb = currencyService.retrieveEnabledCurrencies();

        Mockito.verify(currencyRepository).findCurrenciesByEnabledIsTrue();
        assertEquals(currencies, currenciesDb);
    }

    @Test
    public void testUpdateCurrentPrices() {
        List<Currency> currencies = List.of(TestEntityBuilder.generateTestCurrency(), TestEntityBuilder.generateTestCurrency());
        List<Currency> updatedCurrencies = List.copyOf(currencies);
        updatedCurrencies.forEach(currency -> currency.setCurrentPrice(TestEntityBuilder.generateRandomPrice()));

        Mockito.when(fakeCurrencyPriceApi.fetchCurrencyPrices(currencies)).thenReturn(updatedCurrencies);
        Mockito.when(currencyRepository.findAll()).thenReturn(currencies);

        currencyService.updateCurrentPrices();

        Mockito.verify(fakeCurrencyPriceApi).fetchCurrencyPrices(currencies);
        Mockito.verify(currencyRepository).findAll();
        Mockito.verify(currencyRepository).saveAll(updatedCurrencies);
    }

    @Test
    public void testAddCurrency() {
        Currency currency = TestEntityBuilder.generateTestCurrency();

        Mockito.when(fakeCurrencyPriceApi.fetchCurrencyPrice(currency)).thenReturn(currency);
        Mockito.when(parameterService.retrieveParameterValueListByKey(ParameterKey.UNSUPPORTED_CURRENCIES)).thenReturn(Collections.emptyList());

        currencyService.addCurrency(currency);

        Mockito.verify(fakeCurrencyPriceApi).fetchCurrencyPrice(currency);
        Mockito.verify(currencyRepository).save(currency);
        Mockito.verify(parameterService).retrieveParameterValueListByKey(ParameterKey.UNSUPPORTED_CURRENCIES);
    }
}
