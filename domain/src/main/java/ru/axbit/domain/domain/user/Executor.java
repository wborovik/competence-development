package ru.axbit.domain.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.BatchSize;
import ru.axbit.domain.domain.cls.ClsOrderCategory;
import ru.axbit.domain.domain.cls.ClsWorkSpeed;
import ru.axbit.domain.domain.common.UserData;
import ru.axbit.domain.domain.evaluation.Evaluation;
import ru.axbit.domain.domain.order.WorkOrder;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

/**
 * Класс, описывающий исполнителя заказов.
 */
@Getter
@Setter
@Entity
@FieldNameConstants
public class Executor extends UserData {

    /**
     * Заказы, которые выполняет исполнитель.
     */
    @BatchSize(size = 500)
    @OneToMany(mappedBy = "executor", fetch = FetchType.LAZY)
    private List<WorkOrder> orders;

    /**
     * Оценка, характеризующая исполнителя.
     */
    @OneToOne
    private Evaluation evaluation;

    /**
     * Категория работ, которые выполняет исполнитель.
     */
    @OneToOne
    private ClsOrderCategory workCategory;

    /**
     * Характеристика скорости выполнения работ исполнителем.
     */
    @OneToOne
    private ClsWorkSpeed workSpeed;
}
