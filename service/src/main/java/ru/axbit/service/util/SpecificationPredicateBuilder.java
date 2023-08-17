package ru.axbit.service.util;

import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.*;

import static java.util.stream.Collectors.toList;

@SuppressWarnings({"rawtypes"})
public class SpecificationPredicateBuilder {
    @Getter
    private final Root<?> root;
    private final CriteriaBuilder criteriaBuilder;
    private final CriteriaQuery criteriaQuery;
    protected final List<Predicate> predicates;

    public SpecificationPredicateBuilder(Root<?> root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        this.root = root;
        this.criteriaBuilder = criteriaBuilder;
        this.criteriaQuery = query;
        this.predicates = new ArrayList<>();
    }

    public static SpecificationPredicateBuilder builder(Root<?> root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        return new SpecificationPredicateBuilder(root, query, criteriaBuilder);
    }

    public Predicate build() {
        return predicates.isEmpty() ? null : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    public SpecificationPredicateBuilder equals(Path<?> path, Collection<?> value) {
        if (CollectionUtils.isNotEmpty(value)) {
            predicates.add(path.in(value));
        }
        return this;
    }

    public SpecificationPredicateBuilder sortBy(String customFieldName, Boolean descending, String defaultFieldName) {
        Path path;
        if (Objects.nonNull(customFieldName)) {
            String[] fieldNames = customFieldName.split("\\.");
            List<String> fieldNameCamelCase = Arrays.stream(fieldNames)
                    .collect(toList());
            path = root.get(fieldNameCamelCase.get(0));
        } else {
            path = root.get(defaultFieldName);
            descending = false;
        }
        if (descending) {
            criteriaQuery.orderBy(criteriaBuilder.desc(path));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(path));
        }
        return this;
    }

    public SpecificationPredicateBuilder isDeleted(Path<?> path, Boolean isDeleted) {
        predicates.add(path.in(isDeleted));
        return this;
    }
}
