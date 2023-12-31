package ru.axbit.service.service.soap.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO сущности заказчика {@link ru.axbit.domain.domain.user.Customer}.
 */
@Data
@Builder
public class CustomerCriteriaDTO {
    private List<Long> customerId;
    private List<String> customerName;
    private List<String> customerSurname;
    private List<Integer> customerAge;
    private boolean isDeleted;
}
