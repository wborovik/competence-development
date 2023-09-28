package ru.axbit.domain.domain.cls;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import ru.axbit.domain.domain.cls.common.ClsBase;

import javax.persistence.Entity;

/**
 * Класс, описывающий справочник скорости выполнения работ исполнителем.
 */
@Getter
@Setter
@Entity
@FieldNameConstants
public class ClsWorkSpeed extends ClsBase {
    private String description;
    private String speed;
}
