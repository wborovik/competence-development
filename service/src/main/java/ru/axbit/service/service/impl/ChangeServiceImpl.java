package ru.axbit.service.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axbit.domain.domain.common.Change;
import ru.axbit.domain.repository.ChangeRepository;
import ru.axbit.service.service.ChangeService;
import ru.axbit.service.service.json.JsonMappingService;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Класс реализует методы интерфейса {@link ChangeService} журналирования запросов/ответов в БД.
 */
@Transactional
@Service
@AllArgsConstructor
public class ChangeServiceImpl implements ChangeService {
    private final JsonMappingService jsonMappingService;
    private final ChangeRepository changeRepository;

    /**
     * Общий метод журналирования запросов/ответов в БД.
     *
     * @param request   передается запрос в БД.
     * @param response  передается ответ из БД.
     * @param exception передается исключение, выброшенное в случае, если метод отработал с ошибкой.
     * @param <T>       параметризованный тип.
     * @param <R>       параметризованный тип.
     * @param <E>       параметризованный тип.
     */
    public <T, R, E extends Exception> void requestAndResponseLogging(T request, R response, E exception) {
        Change change = new Change();
        Optional.ofNullable(request).map(jsonMappingService::mapToJsonNode)
                .ifPresent(change::setRequest);
        Optional.ofNullable(response).map(jsonMappingService::mapToJsonNode)
                .ifPresent(change::setResponse);
        addException(exception, change);
        changeRepository.save(change);
    }

    /**
     * Вспомогательный метод, для журналирования исключительных ситуаций, возникающих при обращении к БД.
     *
     * @param exception передается исключение, наследующееся от класса {@link Exception}.
     * @param change    передается объект типа {@link Change}
     * @param <E>       параметризованный тип.
     */
    private <E extends Exception> void addException(E exception, Change change) {
        Optional.ofNullable(exception).ifPresent(e ->
                change.setExceptionMessage(e.getMessage()));
    }
}
