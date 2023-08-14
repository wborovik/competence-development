package ru.axbit.domain.domain.user;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import ru.axbit.domain.domain.order.Order;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.common.UserData;

import javax.validation.constraints.NotNull;
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
