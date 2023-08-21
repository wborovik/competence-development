package ru.axbit.service.service.soap.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderCriteriaDTO {
    private List<Long> orderId;
    private List<Long> customerId;
    private List<Long> executorId;
    private boolean isDeleted = false;
}
