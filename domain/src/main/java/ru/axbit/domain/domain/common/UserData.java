package ru.axbit.domain.domain.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
@FieldNameConstants
public abstract class UserData extends AuditEntity {
    private String name;
    private String surname;
    private Integer age;
}
