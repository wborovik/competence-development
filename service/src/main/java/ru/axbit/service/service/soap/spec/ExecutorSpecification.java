package ru.axbit.service.service.soap.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.axbit.domain.domain.common.AbstractEntity;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.common.UserData;
import ru.axbit.domain.domain.user.Executor;
import ru.axbit.service.service.soap.dto.ExecutorCriteriaDTO;
import ru.axbit.service.util.PagingUtils;
import ru.axbit.service.util.SpecificationPredicateBuilder;

public class ExecutorSpecification {
    public static Specification<Executor> create(ExecutorCriteriaDTO criteriaDTO, PagingUtils.SortingOptions sort) {
        return (root, query, criteriaBuilder) ->
                new SpecificationPredicateBuilder(root, query, criteriaBuilder)
                        .equals(root.get(AbstractEntity.Fields.id), criteriaDTO.getExecutorId())
                        .equals(root.get(UserData.Fields.name), criteriaDTO.getExecutorName())
                        .equals(root.get(UserData.Fields.surname), criteriaDTO.getExecutorSurname())
                        .sortBy(sort.getAttributeName(), sort.getIsDescending(), sort.getDefaultField())
                        .isDeleted(root.get(AuditEntity.Fields.deleted), criteriaDTO.isDeleted())
                        .build();
    }
}
