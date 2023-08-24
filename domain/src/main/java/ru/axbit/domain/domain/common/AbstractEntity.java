package ru.axbit.domain.domain.common;

import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import java.io.Serializable;

/**
 * Абстрактный класс, который является родительским для всех сущностей.
 * В классе определяется индивидуальный идентификатор сущности id.
 * Для автоматической генерации идентификатора используется стратегия глобальный sequence.
 */
@MappedSuperclass
@FieldNameConstants
public abstract class AbstractEntity implements Serializable {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_sequence")
    @SequenceGenerator(name = "global_sequence", allocationSize = 1)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
