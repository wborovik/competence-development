package ru.axbit.domain.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.BatchSize;
import ru.axbit.domain.domain.common.UserData;
import ru.axbit.domain.domain.order.WorkOrder;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Класс, описывающий клиента, делающего заказы.
 */
@Getter
@Setter
@Entity
@FieldNameConstants
public class Customer extends UserData {

    /**
     * Заказы, которые заказал клиент.
     */
    @BatchSize(size = 500)
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<WorkOrder> orders;
}
