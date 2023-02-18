package com.enes.cryptotracker.general.parameter.entity;

import com.enes.cryptotracker.general.audit.AbstractEntityWithAuditColumns;
import com.enes.cryptotracker.general.parameter.entity.enums.ParameterKey;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Parameter extends AbstractEntityWithAuditColumns {
    @Enumerated(EnumType.STRING)
    private ParameterKey key;
    private String value;
}
