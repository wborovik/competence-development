package ru.axbit.service.service.soap.mapper.request;

import ru.axbit.service.service.soap.dto.CustomerCriteriaDTO;
import ru.axbit.service.service.soap.dto.ExecutorCriteriaDTO;
import ru.axbit.service.service.soap.dto.OrderCriteriaDTO;
import ru.axbit.vborovik.competence.filtertypes.v1.GetCustomerListFilterType;
import ru.axbit.vborovik.competence.filtertypes.v1.GetExecutorListFilterType;
import ru.axbit.vborovik.competence.filtertypes.v1.GetOrderListFilterType;

public class CommonMapperDTO {
    public static CustomerCriteriaDTO mapCustomerDTO(GetCustomerListFilterType filter) {
        CustomerCriteriaDTO criteriaDTO = new CustomerCriteriaDTO();
        criteriaDTO.setCustomerId(filter.getCustomerId());
        criteriaDTO.setCustomerName(filter.getCustomerName());
        criteriaDTO.setCustomerSurname(filter.getCustomerSurname());
        return criteriaDTO;
    }

    public static ExecutorCriteriaDTO mapExecutorDTO(GetExecutorListFilterType filter) {
        ExecutorCriteriaDTO criteriaDTO = new ExecutorCriteriaDTO();
        criteriaDTO.setExecutorId(filter.getExecutorId());
        criteriaDTO.setExecutorName(filter.getExecutorName());
        criteriaDTO.setExecutorSurname(filter.getExecutorSurname());
        return criteriaDTO;
    }

    public static OrderCriteriaDTO mapOrderDTO(GetOrderListFilterType filter) {
        OrderCriteriaDTO criteriaDTO = new OrderCriteriaDTO();
        criteriaDTO.setOrderId(filter.getOrderId());
        criteriaDTO.setCustomerId(filter.getCustomerId());
        criteriaDTO.setExecutorId(filter.getExecutorId());
        return criteriaDTO;
    }
}
