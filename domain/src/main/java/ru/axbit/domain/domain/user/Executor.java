package ru.axbit.domain.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
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
public class Executor extends UserData {

    @OneToMany(mappedBy = "executor", fetch = FetchType.LAZY)
    private List<Order> orders;
}
