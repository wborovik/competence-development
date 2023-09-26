package ru.axbit.service.service.soap.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.axbit.domain.domain.common.AbstractEntity;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.order.WorkOrder;
import ru.axbit.domain.domain.transaction.Bill;
import ru.axbit.service.service.soap.dto.BillCriteriaDTO;
import ru.axbit.service.util.PagingUtils;
import ru.axbit.service.util.SpecificationPredicateBuilder;

import javax.persistence.criteria.JoinType;

/**
 * Спецификация получения сущности оплаты заказа {@link Bill} из БД.
 */
public class BillSpecification {
    /**
     * Метод создания спецификации сущности счета на оплату {@link Bill} для выборки данных из БД.
     *
     * @param criteriaDTO входной параметр {@link BillCriteriaDTO} DTO сущности {@link Bill}.
     * @param sort        входной параметр {@link PagingUtils.SortingOptions} свойства сортировки страниц.
     * @return возвращает спецификацию {@link Specification}.
     */
    public static Specification<Bill> create(BillCriteriaDTO criteriaDTO, PagingUtils.SortingOptions sort) {
        return (root, query, criteriaBuilder) -> {
            SpecificationPredicateBuilder predicateBuilder = SpecificationPredicateBuilder
                    .builder(root, query, criteriaBuilder);
            predicateBuilder
                    .equals(root.get(AbstractEntity.Fields.id), criteriaDTO.getBillId())
                    .equals(root.get(Bill.Fields.workOrder).get(AbstractEntity.Fields.id), criteriaDTO.getOrderId())
                    .equals(root.join(Bill.Fields.workOrder, JoinType.INNER)
                            .get(WorkOrder.Fields.customer), criteriaDTO.getCustomerId())
                    .equals(root.join(Bill.Fields.workOrder, JoinType.INNER)
                            .get(WorkOrder.Fields.executor), criteriaDTO.getExecutorId())
                    .sortBy(sort.getAttributeName(), sort.getIsDescending(), sort.getDefaultField())
                    .isDeleted(root.get(AuditEntity.Fields.deleted), criteriaDTO.isDeleted());

            return predicateBuilder.build();
        };
    }
}
