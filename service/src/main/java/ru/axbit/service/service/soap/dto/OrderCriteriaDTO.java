package ru.axbit.service.service.soap.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderCriteriaDTO {
    private List<Long> orderId;
    private List<Long> customerId;
    private List<Long> executorId;
    private String[] orderDataPath;
    private String orderDataValue;
    private boolean isDeleted;
}
