package ru.axbit.domain.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import ru.axbit.domain.domain.common.AuditEntity;

@Getter
@Setter
@Entity
public class Order extends AuditEntity {
}
