package ru.axbit.domain.domain.journal;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
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
 * Класс описывающий сущность для логирования выполняемых методов, их входных и выходных данных.
 */
@Getter
@Setter
@Entity
@FieldNameConstants
@EntityListeners(AuditingEntityListener.class)
public class ActionLog {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "action_log_sequence")
    @SequenceGenerator(name = "action_log_sequence", allocationSize = 1)
    private Long id;
    private String methodName;
    private String request;
    private String response;
    @NotNull
    @CreatedDate
    private LocalDateTime actualDate;
}
