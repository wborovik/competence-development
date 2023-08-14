package ru.axbit.service.service.soap.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerCriteriaDTO {
    private List<Long> customerId;
    private String name;
    private String surname;
    private Integer age;
    private boolean excludeDeleted = false;
}
