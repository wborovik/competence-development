package ru.axbit.service.util;

import java.util.function.Supplier;

/**
 * Служебный класс, содержащий статические методы, помогающие безопасно получать возвращаемые значения.
 */
public class SafeUtils {

    /**
     * Метод для безопасного получения объекта.
     * В случае возникновения исключения, вернется null.
     *
     * @param supplier передается функциональный интерфейс {@link Supplier}.
     * @param <T> параметризованный тип.
     * @return Возвращается либо объект, либо null.
     */
    public static <T> T safeGet(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return null;
        }
    }
}
