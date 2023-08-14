package ru.axbit.domain.domain.admin;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.common.UserData;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class Admin extends AuditEntity {
    @Embedded
    UserData userData;
}
