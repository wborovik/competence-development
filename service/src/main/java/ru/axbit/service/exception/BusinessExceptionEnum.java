package ru.axbit.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Перечисление кастомных бизнес ошибок проекта.
 */
@Getter
@AllArgsConstructor
public enum BusinessExceptionEnum {
    E001("001", "Идентификатор %s должен указывать на существующую запись таблицы %s"),
    E002("002", "Идентификатор %s не должен указывать на удаленную запись таблицы %s"),
    E003("003", "По переданным значениям в таблице %s не найдена ни одна запись"),
    E004("004", "Строку \"%s\" невозможно преобразовать в JSON формат"),
    E005("005", "Идентификатор %s должен указывать на удаленную запись таблицы %s");

    private final String code;
    private final String message;

    /**
     * Метод позволяет выбросить бизнес ошибку, когда условие уже было проверено.
     *
     * @param args принимает массив аргументов.
     */
    public void thr(Object... args) {
        throw new BusinessException(this, args);
    }

    /**
     * Метод позволяет проверить условие и, если потребуется, выбросить бизнес-ошибку.
     *
     * @param isTrue принимает условие или выражение, которое возвращает булево значение.
     * @param args   принимает массив аргументов.
     */
    public void thr(Boolean isTrue, Object... args) {
        if (Boolean.TRUE.equals(isTrue)) {
            return;
        }
        throw new BusinessException(this, args);
    }
}
