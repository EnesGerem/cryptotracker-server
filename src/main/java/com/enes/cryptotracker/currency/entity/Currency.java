package com.enes.cryptotracker.currency.entity;

import com.enes.cryptotracker.currency.entity.supportedcurrency.SupportedCurrency;
import com.enes.cryptotracker.general.audit.AbstractEntityWithAuditColumns;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Builder @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Currency extends AbstractEntityWithAuditColumns {

    @NotNull
    @Column(updatable = false)
    private String name;

    @SupportedCurrency
    @Column(unique = true, updatable = false)
    private String symbol;

    private BigDecimal currentPrice;
    private Boolean enabled;
}
