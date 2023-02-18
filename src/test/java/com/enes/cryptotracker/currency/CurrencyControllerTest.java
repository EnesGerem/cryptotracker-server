package com.enes.cryptotracker.currency;

import com.enes.cryptotracker.currency.controller.CurrencyController;
import com.enes.cryptotracker.currency.controller.dto.CreateCurrencyDTO;
import com.enes.cryptotracker.currency.controller.dto.CurrencyDTO;
import com.enes.cryptotracker.currency.entity.Currency;
import com.enes.cryptotracker.general.AbstractUnitTest;
import com.enes.cryptotracker.general.TestEntityBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyControllerTest extends AbstractUnitTest {

    @InjectMocks
    private CurrencyController currencyController;

    @Mock
    private CurrencyService currencyService;

    @Captor
    private final ArgumentCaptor<Currency> currencyArgumentCaptor = ArgumentCaptor.forClass(Currency.class);

    @Test
    public void testGetEnabledCurrencies() {
        currencyController.getEnabledCurrencies();
        Mockito.verify(currencyService).retrieveEnabledCurrencies();
    }

    @Test
    public void testGetEnabledCurrency() {
        Currency currency = TestEntityBuilder.generateTestCurrency();
        Mockito.when(currencyService.retrieveEnabledCurrencyBySymbol(currency.getSymbol())).thenReturn(currency);

        CurrencyDTO currencyDTO = currencyController.getEnabledCurrency(currency.getSymbol());

        Mockito.verify(currencyService).retrieveEnabledCurrencyBySymbol(currency.getSymbol());

        assertEquals(currency.getSymbol(), currencyDTO.getSymbol());
        assertEquals(currency.getName(), currencyDTO.getName());
    }

    @Test
    public void testCreateCurrency() {
        Currency currency = TestEntityBuilder.generateTestCurrency();
        CreateCurrencyDTO createCurrencyDTO = new CreateCurrencyDTO();
        createCurrencyDTO.setName(currency.getName());
        createCurrencyDTO.setSymbol(currency.getSymbol());

        currencyController.createCurrency(createCurrencyDTO);

        Mockito.verify(currencyService).addCurrency(currencyArgumentCaptor.capture());
        Currency captured = currencyArgumentCaptor.getValue();
        Currency added = createCurrencyDTO.getDomainObject();

        assertEquals(added.getSymbol(), captured.getSymbol());
        assertEquals(added.getName(), captured.getName());
    }

    @Test
    public void testEnableCurrency() {
        Currency currency = TestEntityBuilder.generateTestCurrency();
        currencyController.enableCurrency(currency.getSymbol());
        Mockito.verify(currencyService).enableCurrency(currency.getSymbol());
    }
}
