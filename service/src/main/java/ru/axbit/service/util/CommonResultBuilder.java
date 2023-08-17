package ru.axbit.service.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonResultBuilder {
    @SuppressWarnings("unchecked")
    public static <T, R, E extends Exception> R buildResponse(ExFunction<T, R, E> function, T request) throws E {
        try {
            return function.apply(request);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw (E) new RuntimeException(e);
        }
    }

    @FunctionalInterface
    public interface ExFunction<T, R, E extends Exception> {
        R apply(T t) throws E;
    }
}
