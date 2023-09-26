package ru.axbit.domain.domain.transaction;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.order.WorkOrder;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Счет на оплату.
 */
@Getter
@Setter
@Entity
@FieldNameConstants
public class Bill extends AuditEntity {
    /**
     * Заказ.
     */
    @OneToOne
    private WorkOrder workOrder;

    /**
     * Стоимость выполнения заказа.
     */
    private BigDecimal price;

    /**
     * Статус оплаты заказа.
     */
    @NotNull
    private boolean isPayment;
}
