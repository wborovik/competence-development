package ru.axbit.service.service.soap.mapper.request;

import ru.axbit.service.service.soap.dto.CustomerCriteriaDTO;
import ru.axbit.service.service.soap.dto.ExecutorCriteriaDTO;
import ru.axbit.service.service.soap.dto.OrderCriteriaDTO;
import ru.axbit.vborovik.competence.filtertypes.v1.GetCustomerListFilterType;
import ru.axbit.vborovik.competence.filtertypes.v1.GetExecutorListFilterType;
import ru.axbit.vborovik.competence.filtertypes.v1.GetOrderListFilterType;
import ru.axbit.vborovik.competence.filtertypes.v1.KeyValuePair;

import static java.util.Optional.ofNullable;

public class CommonMapperDTO {
    public static CustomerCriteriaDTO mapCustomerDTO(GetCustomerListFilterType filter) {
        CustomerCriteriaDTO.CustomerCriteriaDTOBuilder builder = CustomerCriteriaDTO.builder();
        return builder
                .customerId(filter.getCustomerId())
                .customerName(filter.getCustomerName())
                .customerSurname(filter.getCustomerSurname())
                .isDeleted(false)
                .build();
    }

    public static ExecutorCriteriaDTO mapExecutorDTO(GetExecutorListFilterType filter) {
        ExecutorCriteriaDTO.ExecutorCriteriaDTOBuilder builder = ExecutorCriteriaDTO.builder();
        return builder
                .executorId(filter.getExecutorId())
                .executorName(filter.getExecutorName())
                .executorSurname(filter.getExecutorSurname())
                .isDeleted(false)
                .build();
    }

    public static OrderCriteriaDTO mapOrderDTO(GetOrderListFilterType filter) {
        OrderCriteriaDTO.OrderCriteriaDTOBuilder builder = OrderCriteriaDTO.builder();
        return builder
                .orderId(filter.getOrderId())
                .customerId(filter.getCustomerId())
                .executorId(filter.getExecutorId())
                .orderDataPath(ofNullable(filter.getOrderData())
                        .flatMap(orgData -> ofNullable(orgData.getKey()))
                        .map(key -> key.split("\\."))
                        .orElse(null))
                .orderDataValue(ofNullable(filter.getOrderData())
                        .map(KeyValuePair::getValue)
                        .filter(orgData -> !orgData.isBlank())
                        .orElse(null))
                .isDeleted(false)
                .build();
    }
}
