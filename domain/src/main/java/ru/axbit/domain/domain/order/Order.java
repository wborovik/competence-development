package ru.axbit.domain.domain.order;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.user.Customer;
import ru.axbit.domain.domain.user.Executor;

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
