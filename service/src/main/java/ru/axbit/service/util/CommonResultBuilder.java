package ru.axbit.service.util;

import lombok.extern.slf4j.Slf4j;
import ru.axbit.service.exception.BusinessException;
import ru.axbit.service.exception.UserServiceExceptionMapper;
import ru.axbit.service.service.ChangeService;

/**
 * Фасад логгера SLF4J.
 */
@Slf4j
public class CommonResultBuilder {

    /**
     * Строитель SOAP ответа.
     *
     * @param function принимает функциональный интерфейс {@link ExFunction}.
     * @param request  принимает параметризованный тип T.
     * @param service принимает сервис, который наследуется от сервиса журналирования {@link ChangeService}
     * @param <T>      Входной параметр.
     * @param <R>      Выходной параметр.
     * @param <E>      Параметр, наследующийся от класса {@link Exception}.
     * @param <S>      Входной параметр, наследующийся от сервиса {@link ChangeService}
     * @return Возвращает параметризованный тип R.
     * @throws E Может пробрасывать исключения.
     */
    @SuppressWarnings("unchecked")
    public static <T, R, E extends Exception, S extends ChangeService> R buildResponse(
            ExFunction<T, R, E> function, T request, S service) throws E {
        try {
            R response = function.apply(request);
            service.requestAndResponseLogging(request, response, null);
            return response;
        } catch (BusinessException e) {
            log.warn(e.getMessage());
            service.requestAndResponseLogging(request, null, e);
            throw (E) UserServiceExceptionMapper.convert(e, request.getClass());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            service.requestAndResponseLogging(request, null, e);
            throw (E) new RuntimeException(e);
        }
    }

    /**
     * Функциональный интерфейс.
     * Модифицированный интерфейс Function.
     * Дополнительно в методе apply добавлена секция throws.
     *
     * @param <T> Входной параметр.
     * @param <R> Выходной параметр.
     * @param <E> Параметр наследующийся от класса {@link Exception}.
     */
    @FunctionalInterface
    public interface ExFunction<T, R, E extends Exception> {
        R apply(T t) throws E;
    }
}
