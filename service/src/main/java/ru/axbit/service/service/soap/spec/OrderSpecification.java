package ru.axbit.service.service.soap.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.axbit.domain.domain.common.AbstractEntity;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.order.Order;
import ru.axbit.service.service.soap.dto.OrderCriteriaDTO;
import ru.axbit.service.util.PagingUtils;
import ru.axbit.service.util.SpecificationPredicateBuilder;

public class OrderSpecification {
    public static Specification<Order> create(OrderCriteriaDTO criteriaDTO, PagingUtils.SortingOptions sort) {
        return (root, query, criteriaBuilder) ->
                new SpecificationPredicateBuilder(root, query, criteriaBuilder)
                        .equals(root.get(AbstractEntity.Fields.id), criteriaDTO.getOrderId())
                        .equals(root.get(Order.Fields.customer).get(AbstractEntity.Fields.id), criteriaDTO.getCustomerId())
                        .equals(root.get(Order.Fields.executor).get(AbstractEntity.Fields.id), criteriaDTO.getExecutorId())
                        .sortBy(sort.getAttributeName(), sort.getIsDescending(), sort.getDefaultField())
                        .isDeleted(root.get(AuditEntity.Fields.deleted), criteriaDTO.isDeleted())
                        .build();
    }
}
