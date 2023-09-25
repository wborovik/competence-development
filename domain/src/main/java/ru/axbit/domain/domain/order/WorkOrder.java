package ru.axbit.domain.domain.order;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.axbit.domain.domain.cls.ClsOrderCategory;
import ru.axbit.domain.domain.cls.ClsOrderStatus;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.transaction.Bill;
import ru.axbit.domain.domain.transaction.Payment;
import ru.axbit.domain.domain.user.Customer;
import ru.axbit.domain.domain.user.Executor;
import com.fasterxml.jackson.databind.JsonNode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Класс, описывающий заказ.
 */
@Getter
@Setter
@Entity
@FieldNameConstants
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class WorkOrder extends AuditEntity {

    /**
     * Описание заказа.
     */
    private String title;

    /**
     * Чек, содержащий цену и код заказа.
     */
    @Type(type = "jsonb")
    private JsonNode orderCheck;

    /**
     * Клиент, который сделал заказ.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    /**
     * Исполнитель, который выполняет заказ.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Executor executor;

    /**
     * Категория заказа.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private ClsOrderCategory category;

    /**
     * Статус заказа.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private ClsOrderStatus status;

    /**
     * Счет на оплату заказа.
     */
    @OneToOne(mappedBy = "workOrder")
    private Bill bill;

    /**
     * Оплата заказа.
     */
    @OneToOne(mappedBy = "workOrder")
    private Payment payment;
}
