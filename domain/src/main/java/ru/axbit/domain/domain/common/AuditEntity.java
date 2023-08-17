package ru.axbit.domain.domain.common;


import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@FieldNameConstants
public abstract class AuditEntity extends AbstractEntity {
    @NotNull
    private LocalDateTime created;
    private LocalDateTime changed;
    @NotNull
    private boolean deleted;
}
