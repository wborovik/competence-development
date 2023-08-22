package ru.axbit.domain.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.BatchSize;
import ru.axbit.domain.domain.common.UserData;
import ru.axbit.domain.domain.order.Order;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Entity
@FieldNameConstants
public class Customer extends UserData {

    @BatchSize(size = 500)
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Order> orders;
}
