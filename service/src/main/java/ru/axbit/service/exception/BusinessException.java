package ru.axbit.service.exception;

import lombok.Getter;

import java.util.Objects;

/**
 * Класс, предоставляющий кастомные бизнес-ошибки.
 */
@Getter
public class BusinessException extends RuntimeException {
    private final BusinessExceptionEnum businessExceptionEnum;
    private final Object[] params;

    public BusinessException(BusinessExceptionEnum businessExceptionEnum, Object... params) {
        super();
        this.businessExceptionEnum = businessExceptionEnum;
        this.params = params;
    }

    @Override
    public String getMessage() {
        return Objects.isNull(params) || params.length == 0
                ? businessExceptionEnum.getMessage()
                : String.format(businessExceptionEnum.getMessage(), params);
    }
}
