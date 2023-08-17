package ru.axbit.service.service.soap.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExecutorCriteriaDTO {
    private List<Long> executorId;
    private List<String> executorName;
    private List<String> executorSurname;
    private List<Integer> executorAge;
    private boolean isDeleted = false;
}
