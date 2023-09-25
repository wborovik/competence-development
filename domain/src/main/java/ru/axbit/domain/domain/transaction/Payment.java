package ru.axbit.domain.domain.transaction;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.order.WorkOrder;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Оплата счета.
 */
@Getter
@Setter
@Entity
@FieldNameConstants
public class Payment extends AuditEntity {
    /**
     * Заказ.
     */
    @OneToOne
    private WorkOrder workOrder;

}
