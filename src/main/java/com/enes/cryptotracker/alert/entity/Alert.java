package com.enes.cryptotracker.alert.entity;

import com.enes.cryptotracker.alert.entity.enums.AlertStatus;
import com.enes.cryptotracker.currency.entity.Currency;
import com.enes.cryptotracker.general.audit.AbstractEntityWithAuditColumns;
import com.enes.cryptotracker.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Builder @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Alert extends AbstractEntityWithAuditColumns {

    @ManyToOne
    private User user;
    @ManyToOne
    private Currency currency;

    private BigDecimal targetPrice;

    @Enumerated(EnumType.STRING)
    private AlertStatus status;

}
