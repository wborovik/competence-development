package ru.axbit.service.util;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import ru.axbit.vborovik.competence.core.v1.PagingOptions;
import ru.axbit.vborovik.competence.core.v1.SortOrder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Служебный класс настроек страниц, получаемых из БД.
 */
public class PagingUtils {
    public static final int FIRST_PAGE_NUMBER = 0;
    public static final int MAX_PAGE_SIZE = 20;
    public static final String FIELD_ID = "id";

    /**
     * Статический POJO класс, описывающий свойства сортировки страниц.
     */
    @Getter
    @Builder
    public static class SortingOptions {
        private String attributeName;
        private Boolean isDescending;
        private String defaultField;
    }

    public static PageRequest getPageRequest(PagingOptions pagingOptions) {
        var options = Optional.ofNullable(pagingOptions).orElse(buildDefaultPageSettings());
        return PageRequest.of(options.getPageNumber(), options.getPageSize());
    }

    /**
     * Статический метод, который возвращает дефолтные настройки страницы.
     *
     * @return Возвращает параметры пагинации {@link PagingOptions}.
     */
    private static PagingOptions buildDefaultPageSettings() {
        PagingOptions pagingOptions = new PagingOptions();
        pagingOptions.setPageNumber(FIRST_PAGE_NUMBER);
        pagingOptions.setPageSize(MAX_PAGE_SIZE);
        return pagingOptions;
    }

    /**
     * Настройка свойств сортировки страниц.
     *
     * @param pagingOptions принимает параметры пагинации {@link PagingOptions}.
     * @param defaultField  принимает имя поля, по которому будет осуществляться сортировка. Тип {@link String}.
     * @return Возвращает свойства сортировки страниц {@link SortingOptions}
     */
    public static SortingOptions getSortOptions(PagingOptions pagingOptions, String defaultField) {
        var sortingOptions = SortingOptions.builder().defaultField(defaultField).isDescending(false);
        if (Objects.isNull(pagingOptions)) {
            return sortingOptions.build();
        }
        var attributeName = Optional.ofNullable(pagingOptions.getSortingOptions())
                .map(ru.axbit.vborovik.competence.core.v1.SortingOptions::getSortOrder)
                .map(SortOrder::getAttributeName).orElse(FIELD_ID);
        var isDescending = Optional.ofNullable(pagingOptions.getSortingOptions())
                .map(ru.axbit.vborovik.competence.core.v1.SortingOptions::getSortOrder)
                .map(SortOrder::isDesceding).orElse(false);
        return sortingOptions
                .attributeName(attributeName)
                .isDescending(isDescending)
                .defaultField(defaultField)
                .build();
    }

    /**
     * Метод постраничной обработки данных.
     *
     * @param sliceProducer возвращает объект типа {@link Slice}.
     * @param sliceConsumer обрабатывает сущность.
     * @param maxSliceSize  передается максимальный размер {@link Slice}.
     * @param orders        передается массив сортировок.
     * @param <T>           параметризованный тип.
     */
    public static <T> void forEachPage(Function<Pageable, Slice<? extends T>> sliceProducer,
                                       Consumer<? super T> sliceConsumer,
                                       int maxSliceSize,
                                       Sort.Order... orders) {
        forEachPageList(sliceProducer, list -> list.forEach(sliceConsumer), maxSliceSize, orders);
    }

    /**
     * Метод постраничной обработки данных списка сущностей.
     *
     * @param sliceProducer возвращает объект типа {@link Slice}.
     * @param sliceConsumer обрабатывает список сущностей.
     * @param maxSliceSize  передается максимальный размер {@link Slice}.
     * @param orders        передается массив сортировок.
     * @param <T>           параметризованный тип.
     */
    public static <T> void forEachPageList(Function<Pageable, Slice<? extends T>> sliceProducer,
                                           Consumer<List<? extends T>> sliceConsumer,
                                           int maxSliceSize,
                                           Sort.Order... orders) {
        Pageable page = PageRequest.of(0, maxSliceSize, Sort.by(orders));
        Slice<? extends T> slice;
        do {
            slice = sliceProducer.apply(page);
            sliceConsumer.accept(slice.getContent());
            page = slice.nextPageable();
        } while (slice.hasNext());
    }
}
