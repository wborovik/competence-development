package ru.axbit.service.service.soap.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.axbit.domain.domain.common.AbstractEntity;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.common.UserData;
import ru.axbit.domain.domain.user.Customer;
import ru.axbit.service.service.soap.dto.CustomerCriteriaDTO;
import ru.axbit.service.util.PagingUtils;
import ru.axbit.service.util.SpecificationPredicateBuilder;

/**
 * Спецификация получения сущности заказчика {@link Customer} из БД.
 */
public class CustomerSpecification {
    /**
     * Метод создания спецификации сущности заказчика {@link Customer} для выборки данных из БД.
     *
     * @param criteriaDTO входной параметр {@link CustomerCriteriaDTO} DTO сущности {@link Customer}.
     * @param sort входной параметр {@link PagingUtils.SortingOptions} свойства сортировки страниц.
     * @return Возвращает спецификацию {@link Specification}.
     */
    public static Specification<Customer> create(CustomerCriteriaDTO criteriaDTO, PagingUtils.SortingOptions sort) {
        return (root, query, criteriaBuilder) ->
                new SpecificationPredicateBuilder(root, query, criteriaBuilder)
                        .equals(root.get(AbstractEntity.Fields.id), criteriaDTO.getCustomerId())
                        .equals(root.get(UserData.Fields.name), criteriaDTO.getCustomerName())
                        .equals(root.get(UserData.Fields.surname), criteriaDTO.getCustomerSurname())
                        .sortBy(sort.getAttributeName(), sort.getIsDescending(), sort.getDefaultField())
                        .isDeleted(root.get(AuditEntity.Fields.deleted), criteriaDTO.isDeleted())
                        .build();
    }
}
