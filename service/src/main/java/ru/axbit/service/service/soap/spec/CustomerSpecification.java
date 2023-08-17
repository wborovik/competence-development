package ru.axbit.service.service.soap.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.axbit.domain.domain.user.Customer;
import ru.axbit.service.service.soap.dto.CustomerCriteriaDTO;
import ru.axbit.service.util.PagingUtils;
import ru.axbit.service.util.SpecificationPredicateBuilder;

public class CustomerSpecification {
    public static Specification<Customer> create(CustomerCriteriaDTO criteriaDTO, PagingUtils.SortingOptions sort) {
        return (root, query, criteriaBuilder) ->
                new SpecificationPredicateBuilder(root, query, criteriaBuilder)
                        .equals(root.get("id"), criteriaDTO.getCustomerId())
                        .sortBy(sort.getAttributeName(), sort.getIsDescending(), sort.getDefaultField())
                        .build();
    }
}
