package ru.axbit.domain.domain.common;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class UserData {
    private String name;
    private String surname;
    private Integer age;
}
