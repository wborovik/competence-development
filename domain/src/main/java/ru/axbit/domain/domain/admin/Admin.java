package ru.axbit.domain.domain.admin;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import ru.axbit.domain.domain.common.UserData;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
@FieldNameConstants
public class Admin extends UserData {

}
