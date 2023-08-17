package ru.axbit.service.service.soap.mapper.response;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.axbit.vborovik.competence.core.v1.PagingResults;

@Service
@AllArgsConstructor
public class CommonMapper {
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
