package com.enes.cryptotracker.general.audit;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntityWithAuditColumns extends Auditable {
    @Id
    @GeneratedValue
    private Long id;
}
