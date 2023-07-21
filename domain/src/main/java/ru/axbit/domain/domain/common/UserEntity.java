package ru.axbit.domain.domain.common;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class UserEntity extends AuditEntity {
    private String name;
    private String surname;
    private int age;
}
