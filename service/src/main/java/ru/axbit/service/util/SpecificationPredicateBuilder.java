package ru.axbit.service.util;

import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * Класс-построитель предикатов для спецификаций.
 */
@SuppressWarnings({"rawtypes"})
public class SpecificationPredicateBuilder {
    @Getter
    private final Root<?> root;
    private final CriteriaBuilder criteriaBuilder;
    private final CriteriaQuery criteriaQuery;
    protected final List<Predicate> predicates;

    /**
     * Конструктор с параметрами класса {@link SpecificationPredicateBuilder}.
     *
     * @param root            передается параметр типа {@link Root}.
     * @param query           передается параметр типа {@link CriteriaQuery}.
     * @param criteriaBuilder передается параметр типа {@link CriteriaBuilder}.
     */
    public SpecificationPredicateBuilder(Root<?> root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        this.root = root;
        this.criteriaBuilder = criteriaBuilder;
        this.criteriaQuery = query;
        this.predicates = new ArrayList<>();
    }

    /**
     * Статический метод фабрика.
     * Возвращает новый объект класса {@link SpecificationPredicateBuilder}.
     *
     * @param root            передается параметр типа {@link Root}.
     * @param query           передается параметр типа {@link CriteriaQuery}.
     * @param criteriaBuilder передается параметр типа {@link CriteriaBuilder}.
     * @return Возвращает новый объект класса {@link SpecificationPredicateBuilder}.
     */
    public static SpecificationPredicateBuilder builder(Root<?> root, CriteriaQuery query,
                                                        CriteriaBuilder criteriaBuilder) {
        return new SpecificationPredicateBuilder(root, query, criteriaBuilder);
    }

    /**
     * Метод, возвращающий {@link Predicate}.
     * Если список {@link #predicates} пустой, возвращается null.
     *
     * @return возвращает {@link Predicate}.
     */
    public Predicate build() {
        return predicates.isEmpty() ? null
                :
                criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    /**
     * Метод сравнивает переданные значения поля со значениями в БД.
     *
     * @param path  принимает {@link Path} требуемого поля.
     * @param value принимает коллекцию переданных значений {@link Collection}.
     * @return Возвращает {@link SpecificationPredicateBuilder}.
     */
    public SpecificationPredicateBuilder equals(Path<?> path, Collection<?> value) {
        if (CollectionUtils.isNotEmpty(value)) {
            predicates.add(path.in(value));
        }
        return this;
    }

    /**
     * Метод сравнивает на равенство переданные выражения со значениями в БД.
     *
     * @param path  принимает выражение {@link Expression} для определения требуемого поля.
     * @param value принимает выражение {@link Expression} для определения требуемых значений.
     */
    public void equals(Expression<?> path, Expression<?> value) {
        if (value != null) {
            predicates.add(criteriaBuilder.equal(path, value));
        }
    }

    /**
     * Метод для сортировки возвращаемых из БД значений.
     *
     * @param customFieldName  принимает имя поля, по которому будет производиться сортировка.
     *                         Тип {@link String}.
     * @param descending       принимает восходящее или нисходящее условие сортировки.
     *                         Тип {@link Boolean}.
     * @param defaultFieldName принимает имя дефолтного поля, если не передано или не найдено поле customFieldName.
     *                         Тип {@link String}.
     * @return Возвращает {@link SpecificationPredicateBuilder}.
     */
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

    public void isNonNull(Expression<?> expression) {
        predicates.add(criteriaBuilder.isNotNull(expression));
    }
}
