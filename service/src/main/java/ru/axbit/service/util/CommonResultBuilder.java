package ru.axbit.service.util;

import lombok.extern.slf4j.Slf4j;

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
     * @param <T>      Входной параметр.
     * @param <R>      Выходной параметр.
     * @param <E>      Параметр наследующийся от класса {@link Exception}.
     * @return Возвращает параметризованный тип R.
     * @throws E Может пробрасывать исключения.
     */
    @SuppressWarnings("unchecked")
    public static <T, R, E extends Exception> R buildResponse(ExFunction<T, R, E> function, T request) throws E {
        try {
            return function.apply(request);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
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
