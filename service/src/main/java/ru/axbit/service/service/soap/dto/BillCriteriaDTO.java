package ru.axbit.service.service.soap.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO сущности счета на оплату {@link ru.axbit.domain.domain.transaction.Bill}.
 */
@Data
@Builder
public class BillCriteriaDTO {
    private List<Long> billId;
    private List<Long> orderId;
    private List<Long> customerId;
    private List<Long> executorId;
    private boolean isDeleted;
}
