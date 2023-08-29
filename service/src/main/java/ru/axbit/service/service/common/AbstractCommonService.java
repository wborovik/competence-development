package ru.axbit.service.service.common;

import ru.axbit.domain.domain.common.AbstractEntity;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.common.UserData;
import ru.axbit.domain.repository.common.AbstractRepository;
import ru.axbit.service.util.ValidationUtils;
import ru.axbit.vborovik.competence.filtertypes.v1.CreateOrEditUserDataType;

import java.util.Objects;
import java.util.Optional;

/**
 * Абстрактный базовый класс для сервисов сущностей.
 * Реализует общие для сущностей методы.
 */
public abstract class AbstractCommonService {

    /**
     * Вспомогательный метод, который делает проверку, удалена ли данная запись в таблице БД.
     * Если удалена, для записи меняется статус на "активна".
     * Если запись не удалена, будет выброшено соответствующее бизнес исключение.
     *
     * @param entity    передаются результаты поиска в БД сущности, расширяющей абстрактный класс {@link AuditEntity}.
     * @param entityId  передается идентификатор типа {@link Long}, по которому производился поиск записи.
     * @param tableName передается имя таблицы типа {@link String}, в которой осуществлялся поиск.
     * @param <T>       параметризованный тип.
     */
    public <T extends AuditEntity> void activateEntity(T entity, Long entityId, String tableName) {
        ValidationUtils.checkIsNotDeleted(entity, entityId, tableName);
        entity.activate();
    }

    /**
     * Вспомогательный метод, который делает проверку, удалена ли данная запись в БД.
     * Если не удалена, для записи меняется статус на "удалена".
     * Если удалена, будет выброшено соответствующее бизнес исключение.
     *
     * @param entity    передаются результаты поиска в БД сущности, расширяющей абстрактный класс {@link AuditEntity}.
     * @param entityId  передается идентификатор типа {@link Long}, по которому производился поиск записи.
     * @param tableName передается имя таблицы типа {@link String}, в которой осуществлялся поиск.
     * @param <T>       параметризованный тип.
     */
    public <T extends AuditEntity> void deleteEntity(T entity, Long entityId, String tableName) {
        ValidationUtils.checkIsDeleted(entity, entityId, tableName);
        entity.setDeleted(true);
    }

    /**
     * Метод для изменения данных пользователя.
     * Используется для сущностей, которые расширяют абстрактный класс {@link UserData}.
     *
     * @param entity     передается сущность, расширяющая класс {@link UserData}.
     * @param userData   передается SOAP тип, в котором содержатся передаваемые данные для сохранения или изменения.
     * @param repository передается репозиторий сущности, реализующий интерфейс {@link AbstractRepository}.
     * @param <T>        параметризованный тип.
     * @param <R>        параметризованный тип.
     */
    public <T extends UserData, R extends AbstractRepository<T>> void setUserData(
            T entity, R repository, CreateOrEditUserDataType userData) {
        if (Objects.isNull(userData)) {
            return;
        }
        Optional.ofNullable(userData.getUserName()).ifPresent(entity::setName);
        Optional.ofNullable(userData.getUserSurname()).ifPresent(entity::setSurname);
        Optional.ofNullable(userData.getUserAge()).ifPresent(entity::setAge);

        repository.save(entity);
    }

    /**
     * Метод для нахождения записи в БД по переданному идентификатору.
     * Если запись существует, метод вернёт сущность, требуемого типа.
     * Если такой записи нет, будет выброшено соответствующее исключение.
     *
     * @param id         передается идентификатор типа {@link Long} по которому осуществляется поиск в таблице БД.
     * @param repository передается репозиторий сущности, реализующий интерфейс {@link AbstractRepository}.
     * @param tableName  передается имя таблицы типа {@link String}, в которой осуществлялся поиск.
     * @param <T>        параметризованный тип.
     * @param <R>        параметризованный тип.
     * @return возвращается сущность, которая расширяет абстрактный класс {@link AbstractEntity}.
     */
    public <T extends AbstractEntity, R extends AbstractRepository<T>> T findEntityById(
            Long id, R repository, String tableName) {

        return repository.findById(id)
                .orElseThrow(() -> ValidationUtils.throwExceptionIfNotExists(id, tableName));
    }
}
