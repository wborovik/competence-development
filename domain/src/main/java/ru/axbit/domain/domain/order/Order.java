package ru.axbit.domain.domain.order;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import ru.axbit.domain.domain.cls.ClsOrderCategory;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.user.Customer;
import ru.axbit.domain.domain.user.Executor;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "orders")
@FieldNameConstants
public class Order extends AuditEntity {

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Executor executor;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClsOrderCategory category;
}
