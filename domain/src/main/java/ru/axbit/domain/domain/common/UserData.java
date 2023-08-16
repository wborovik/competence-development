package ru.axbit.domain.domain.common;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class UserData {
    private String name;
    private String surname;
    private Integer age;
}
