package ru.axbit.domain.domain.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.MappedSuperclass;

/**
 * Абстрактный класс, описывающий общие свойства сущностей пользователей.
 */
@Getter
@Setter
@MappedSuperclass
@FieldNameConstants
public abstract class UserData extends AuditEntity {
    /**
     * Имя пользователя.
     */
    private String name;

    /**
     * Фамилия пользователя.
     */
    private String surname;

    /**
     * Возраст пользователя.
     */
    private Integer age;
}
