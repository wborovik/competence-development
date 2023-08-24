package ru.axbit.domain.domain.cls;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.BatchSize;
import ru.axbit.domain.domain.cls.common.ClsBase;
import ru.axbit.domain.domain.order.WorkOrder;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Класс, описывающий справочник категории заказа.
 */
@Getter
@Setter
@Entity
@FieldNameConstants
public class ClsOrderCategory extends ClsBase {
    private String designation;

    private String description;

    @BatchSize(size = 500)
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<WorkOrder> orders;
}
