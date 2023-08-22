package ru.axbit.domain.domain.order;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.axbit.domain.domain.cls.ClsOrderCategory;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.user.Customer;
import ru.axbit.domain.domain.user.Executor;
import com.fasterxml.jackson.databind.JsonNode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "orders")
@FieldNameConstants
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Order extends AuditEntity {

    private String title;

    @Type(type = "jsonb")
    private JsonNode check;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Executor executor;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private ClsOrderCategory category;
}
