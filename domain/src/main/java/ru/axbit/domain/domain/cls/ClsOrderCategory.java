package ru.axbit.domain.domain.cls;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import ru.axbit.domain.domain.cls.common.ClsBase;

import javax.persistence.Entity;

/**
 * Класс, описывающий справочник категории заказа.
 */
@Getter
@Setter
@Entity
@FieldNameConstants
public class ClsOrderCategory extends ClsBase {
    private String designation;

    private String description;
}
