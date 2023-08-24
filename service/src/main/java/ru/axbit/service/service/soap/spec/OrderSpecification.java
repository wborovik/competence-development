package ru.axbit.service.service.soap.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.axbit.domain.domain.common.AbstractEntity;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.order.WorkOrder;
import ru.axbit.service.service.soap.dto.OrderCriteriaDTO;
import ru.axbit.service.util.PagingUtils;
import ru.axbit.service.util.SpecificationPredicateBuilder;

import javax.persistence.criteria.Expression;
import java.util.Optional;

/**
 * Спецификация получения сущности заказа {@link WorkOrder} из БД.
 */
public class OrderSpecification {
    /**
     * Метод создания спецификации сущности заказа {@link WorkOrder} для выборки данных из БД.
     *
     * @param criteriaDTO входной параметр {@link OrderCriteriaDTO} DTO сущности {@link WorkOrder}.
     * @param sort входной параметр {@link PagingUtils.SortingOptions} свойства сортировки страниц.
     * @return Возвращает спецификацию {@link Specification}.
     */
    public static Specification<WorkOrder> create(OrderCriteriaDTO criteriaDTO, PagingUtils.SortingOptions sort) {
        return (root, query, criteriaBuilder) -> {
            SpecificationPredicateBuilder predicateBuilder = SpecificationPredicateBuilder
                    .builder(root, query, criteriaBuilder);
            predicateBuilder
                    .equals(root.get(AbstractEntity.Fields.id), criteriaDTO.getOrderId())
                    .equals(root.get(WorkOrder.Fields.customer).get(AbstractEntity.Fields.id),
                            criteriaDTO.getCustomerId())
                    .equals(root.get(WorkOrder.Fields.executor).get(AbstractEntity.Fields.id),
                            criteriaDTO.getExecutorId())
                    .sortBy(sort.getAttributeName(), sort.getIsDescending(), sort.getDefaultField())
                    .isDeleted(root.get(AuditEntity.Fields.deleted), criteriaDTO.isDeleted());
            Optional.ofNullable(criteriaDTO.getOrderDataPath())
                    .filter(orderPath -> orderPath.length != 0)
                    .ifPresent(orderPath -> {
                        int argLength = orderPath.length + 1;
                        Expression<?>[] functionArgs = new Expression<?>[argLength];
                        functionArgs[0] = root.get("orderCheck");
                        for (int i = 1; i < functionArgs.length; i++) {
                            functionArgs[i] = criteriaBuilder.literal(orderPath[i - 1]);
                        }
                        Optional.ofNullable(criteriaDTO.getOrderDataValue())
                                .ifPresentOrElse(orderValue -> predicateBuilder
                                                .equals(criteriaBuilder.function("jsonb_extract_path_text",
                                                                String.class, functionArgs),
                                                        criteriaBuilder.literal(orderValue)),
                                        () -> predicateBuilder.isNonNull(criteriaBuilder
                                                .function("jsonb_extract_path", Object.class, functionArgs)));
                    });
            return predicateBuilder.build();
        };
    }
}
