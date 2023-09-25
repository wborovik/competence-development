package ru.axbit.domain.domain.evaluation;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.user.Executor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Оценка, характеризующая исполнителя {@link Executor} работ.
 */
@Getter
@Setter
@Entity
@FieldNameConstants
public class Evaluation extends AuditEntity {

    /**
     * Исполнитель работ.
     */
    @OneToOne(mappedBy = "evaluation")
    private Executor executor;

    /**
     * Средняя оценка, характеризующая исполнителя.
     */
    @Min(0)
    @Max(5)
    private double evaluation;

    /**
     * Счетчик количества поставленных оценок.
     */
    private long count;
}
