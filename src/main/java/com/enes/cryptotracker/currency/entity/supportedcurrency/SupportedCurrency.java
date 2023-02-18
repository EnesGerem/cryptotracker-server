package com.enes.cryptotracker.currency.entity.supportedcurrency;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = { SupportedCurrencyValidator.class })
public @interface SupportedCurrency {
    String message() default "Currency is not supported";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
