package ru.axbit.domain.domain.user;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.common.UserData;
import ru.axbit.domain.domain.order.Order;

import java.util.List;

@Getter
@Setter
@Entity
public class Executor extends AuditEntity {
    @Embedded
    UserData userData;

    @OneToMany(mappedBy = "executor")
    private List<Order> orders;
}
