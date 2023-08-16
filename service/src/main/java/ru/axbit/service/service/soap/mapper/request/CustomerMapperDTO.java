package ru.axbit.service.service.soap.mapper.request;

import ru.axbit.service.service.soap.dto.CustomerCriteriaDTO;
import ru.axbit.vborovik.competence.filtertypes.v1.GetCustomerListFilterType;

public class CustomerMapperDTO {
    public static CustomerCriteriaDTO mapCustomerDTO(GetCustomerListFilterType filter) {
        CustomerCriteriaDTO criteriaDTO = new CustomerCriteriaDTO();
        criteriaDTO.setCustomerId(filter.getCustomerId());
        return criteriaDTO;
    }
}
