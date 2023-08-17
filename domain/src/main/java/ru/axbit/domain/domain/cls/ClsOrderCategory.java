package ru.axbit.domain.domain.cls;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import ru.axbit.domain.domain.cls.common.ClsBase;
import ru.axbit.domain.domain.order.Order;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Entity
@FieldNameConstants
public class ClsOrderCategory extends ClsBase {
    private String designation;

    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Order> orders;
}
