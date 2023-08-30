package ru.axbit.domain.domain.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Класс, описывающий журнал запросов/ответов в БД.
 */
@Getter
@Setter
@Entity
@FieldNameConstants
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Change {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "change_sequence")
    @SequenceGenerator(name = "change_sequence", allocationSize = 1)
    private Long id;

    /**
     * Поле запроса. При вызове метода, запрос сохраняется в таблице change.
     */
    @Type(type = "jsonb")
    private JsonNode request;

    /**
     * Поле ответа.
     * Если вызванный метод отработал без ошибок и был получен ответ, то он сохраняется в таблице change.
     */
    @Type(type = "jsonb")
    private JsonNode response;

    /**
     * Поле сообщения об ошибке.
     * Если при вызове метода возникла исключительная ситуация, то сообщение данного исключения сохранится в таблице.
     */
    private String exceptionMessage;

    /**
     * Время создания записи.
     */
    @NotNull
    @CreatedDate
    private LocalDateTime created;
}
