package ru.axbit.domain.domain.user;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.common.UserData;
import ru.axbit.domain.domain.order.Order;

import java.util.List;

@Getter
@Setter
@Entity
public class Customer extends AuditEntity {
    @Embedded
    UserData userData;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
