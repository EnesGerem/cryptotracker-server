package com.enes.cryptotracker.scheduled;

import com.enes.cryptotracker.alert.service.AlertService;
import com.enes.cryptotracker.currency.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class ScheduledJobs {
    private final CurrencyService currencyService;
    private final AlertService alertService;

    @Scheduled(cron = "${currency.fetch.cron}")
    public void updateCurrencyPrices() {
        currencyService.updateCurrentPrices();
    }

    @Scheduled(cron = "${alert.check.cron}")
    public void checkAlerts() {
        alertService.checkAlerts();
    }
}
