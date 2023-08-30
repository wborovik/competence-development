package ru.axbit.service.service;

/**
 * Интерфейс, описывающий методы журналирования запросов/ответов в БД.
 */
public interface ChangeService {
    <T, R, E extends Exception> void requestAndResponseLogging(T request, R response, E exception);
}
