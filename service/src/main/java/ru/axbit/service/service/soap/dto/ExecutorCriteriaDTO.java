package ru.axbit.service.service.soap.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO сущности исполнителя Executor.
 */
@Data
@Builder
public class ExecutorCriteriaDTO {
    private List<Long> executorId;
    private List<String> executorName;
    private List<String> executorSurname;
    private List<Integer> executorAge;
    private boolean isDeleted;
}
