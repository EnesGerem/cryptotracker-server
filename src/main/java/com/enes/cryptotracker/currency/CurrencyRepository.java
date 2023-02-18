package com.enes.cryptotracker.currency;

import com.enes.cryptotracker.currency.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findCurrencyBySymbolAndEnabledIsTrue(String symbol);
    Optional<Currency> findCurrencyBySymbolAndEnabledIsFalse(String symbol);
    Optional<List<Currency>> findCurrenciesByEnabledIsTrue();
}
