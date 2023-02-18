package com.enes.cryptotracker.general.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing(auditorAwareRef = "aware")
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> aware() {
        return new Aware();
    }
}
