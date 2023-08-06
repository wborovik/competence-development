package ru.axbit.domain.domain.common;

import jakarta.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class AuditEntity extends AbstractEntity {
    @NotNull
    private LocalDateTime createdDate;
    private LocalDateTime changedDate;
    @NotNull
    private boolean isDeleted;
}
