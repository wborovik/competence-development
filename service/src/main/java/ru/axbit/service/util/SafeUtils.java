package ru.axbit.service.util;

import java.util.function.Supplier;

public class SafeUtils {
    public static <T> T safeGet(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return null;
        }
    }
}
