package com.enes.cryptotracker.currency.entity.supportedcurrency;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class SupportedCurrencyValidator implements ConstraintValidator<SupportedCurrency, String> {

    private final List<String> unsupportedCurrencies = List.of("ETH", "LTC", "ZKN", "MRD", "LPR", "GBZ");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !value.isEmpty() && !unsupportedCurrencies.contains(value);
    }
}
