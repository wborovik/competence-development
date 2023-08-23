package ru.axbit.domain.domain.order;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.axbit.domain.domain.cls.ClsOrderCategory;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.user.Customer;
import ru.axbit.domain.domain.user.Executor;
import com.fasterxml.jackson.databind.JsonNode;

import javax.persistence.*;

@Getter
@Setter
@Entity
@FieldNameConstants
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class WorkOrder extends AuditEntity {

    private String title;

    @Type(type = "jsonb")
    private JsonNode orderCheck;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Executor executor;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClsOrderCategory category;
}
