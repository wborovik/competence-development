package ru.axbit.service.service.soap.mapper.response;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.axbit.vborovik.competence.core.v1.PagingResults;

/**
 * Класс для общих методов мапинга.
 */
@Service
@AllArgsConstructor
@SuppressWarnings("rawtypes")
public class CommonMapper {
    /**
     * Метод, преобразующий Page к списку пагинации.
     * @param pagingTypeClazz Класс.
     * @param page страница Page.
     * @return Возвращает список атрибутов пагинации.
     * @param <T> Параметризованный тип.
     *           Наследует класс, описывающий пагинацию.
     * @param <U> Параметризованный тип.
     *           Наследует класс страницы Page.
     */
    @SneakyThrows
    public static <T extends PagingResults, U extends Page> T mapPagingResults(Class<T> pagingTypeClazz, U page) {
        var pagingType = pagingTypeClazz.getDeclaredConstructor().newInstance();
        pagingType.setPageNumber(page.getNumber());
        pagingType.setPageTotal(page.getTotalPages());
        pagingType.setMorePagesAvailable(page.hasNext());
        pagingType.setTotalItemsCount(page.getTotalElements());
        pagingType.setPageSize(page.getNumberOfElements());

        return pagingType;
    }
}
