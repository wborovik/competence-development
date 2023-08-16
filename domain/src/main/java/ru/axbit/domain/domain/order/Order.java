package ru.axbit.domain.domain.order;


import lombok.Getter;
import lombok.Setter;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.user.Customer;
import ru.axbit.domain.domain.user.Executor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends AuditEntity {
    @ManyToOne
    Customer customer;
    @ManyToOne
    Executor executor;
}
