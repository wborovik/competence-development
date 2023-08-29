package ru.axbit.service.util;

import org.springframework.data.domain.Page;
import ru.axbit.domain.domain.common.AbstractEntity;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.service.exception.BusinessException;
import ru.axbit.service.exception.BusinessExceptionEnum;

import java.util.Optional;

/**
 * Вспомогательный класс, содержащий статические методы валидации данных.
 */
public class ValidationUtils {

    /**
     * Метод проверяет, является ли сущность неудаленной.
     * Если сущность не удалена, будет выброшено соответствующее исключение {@link BusinessExceptionEnum#E005}.
     *
     * @param auditEntity передается сущность, которая наследует абстрактный класс {@link AuditEntity}.
     * @param entityId    передается идентификатор типа {@link Long}, по которому производился поиск записи.
     * @param simpleName  передается имя таблицы типа {@link String}, в которой осуществлялся поиск.
     * @param <T>         параметризованный тип.
     */
    public static <T extends AuditEntity> void checkIsNotDeleted(T auditEntity, Long entityId, String simpleName) {
        Optional.ofNullable(auditEntity)
                .filter(AuditEntity::nonDeleted)
                .ifPresent(entity -> BusinessExceptionEnum.E005.thr(entityId, simpleName));
    }

    /**
     * Метод проверяет, является ли сущность удаленной.
     * Если сущность удалена, будет выброшено соответствующее исключение {@link BusinessExceptionEnum#E002}.
     *
     * @param auditEntity передается сущность, которая наследует абстрактный класс {@link AuditEntity}.
     * @param entityId    передается идентификатор типа {@link Long}, по которому производился поиск записи.
     * @param simpleName  передается имя таблицы типа {@link String}, в которой осуществлялся поиск.
     * @param <T>         параметризованный тип.
     */
    public static <T extends AuditEntity> void checkIsDeleted(T auditEntity, Long entityId, String simpleName) {
        Optional.ofNullable(auditEntity)
                .filter(AuditEntity::isDeleted)
                .ifPresent(entity -> BusinessExceptionEnum.E002.thr(entityId, simpleName));
    }

    /**
     * Метод выбрасывает соответствующее исключение, когда при поиске в БД требуемая запись не была найдена,
     * и проверка на соответствие условию уже была осуществлена.
     *
     * @param entityId   передается идентификатор типа {@link Long}, по которому производился поиск записи.
     * @param simpleName передается имя таблицы типа {@link String}, в которой осуществлялся поиск.
     * @return возвращается соответствующее бизнес исключение {@link BusinessExceptionEnum#E001}.
     */
    public static BusinessException throwExceptionIfNotExists(Long entityId, String simpleName) {
        return new BusinessException(BusinessExceptionEnum.E001, entityId, simpleName);
    }

    /**
     * @param page      передается страница {@link Page}, которая была получена в результате поиска в БД.
     * @param tableName передается имя таблицы типа {@link String}, в которой осуществлялся поиск.
     * @param <T>       параметризованный тип.
     */
    public static <T extends AbstractEntity> void checkIsEmptyPage(Page<T> page, String tableName) {
        BusinessExceptionEnum.E003.thr(!page.isEmpty(), tableName);
    }
}
