package ru.axbit.domain.domain.common;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class AuditEntity extends AbstractEntity {
    private LocalDateTime createdDate;
    private LocalDateTime changedDate;
    private boolean isDeleted;
}
