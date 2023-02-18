package com.enes.cryptotracker.currency;

import com.enes.cryptotracker.currency.entity.Currency;
import com.enes.cryptotracker.general.AbstractRepositoryTest;
import com.enes.cryptotracker.general.TestEntityBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CurrencyRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    public void testFindCurrencyBySymbolAndEnabledIsTrue() {
        Currency currency = TestEntityBuilder.generateTestCurrency();
        testEntityManager.persist(currency);

        Optional<Currency> currencyOptional = currencyRepository.findCurrencyBySymbolAndEnabledIsTrue(currency.getSymbol());

        assertTrue(currencyOptional.isPresent());
        assertEquals(currency.getSymbol(), currencyOptional.get().getSymbol());
    }

    @Test
    public void testFindCurrencyBySymbolAndEnabledIsFalse() {
        Currency currency = TestEntityBuilder.generateTestCurrency();
        currency.setEnabled(false);
        testEntityManager.persist(currency);

        Optional<Currency> currencyOptional = currencyRepository.findCurrencyBySymbolAndEnabledIsFalse(currency.getSymbol());

        assertTrue(currencyOptional.isPresent());
        assertEquals(currency.getSymbol(), currencyOptional.get().getSymbol());
    }

    @Test
    public void findCurrenciesByEnabledIsTrue() {
        Currency notEnabledCurrency = TestEntityBuilder.generateTestCurrency();
        notEnabledCurrency.setEnabled(false);
        List<Currency> currencies = List.of(TestEntityBuilder.generateTestCurrency(), TestEntityBuilder.generateTestCurrency(), notEnabledCurrency);
        currencies.forEach(testEntityManager::persist);

        Optional<List<Currency>> optionalCurrencies = currencyRepository.findCurrenciesByEnabledIsTrue();

        assertTrue(optionalCurrencies.isPresent());
        assertEquals(2, optionalCurrencies.get().size());
    }
}
