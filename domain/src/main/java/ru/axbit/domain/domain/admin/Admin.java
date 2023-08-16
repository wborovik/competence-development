package ru.axbit.domain.domain.admin;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.common.UserData;

@Getter
@Setter
@Entity
public class Admin extends AuditEntity {
    @Embedded
    UserData userData;
}
