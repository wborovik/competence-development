package ru.axbit.service.service;

import ru.axbit.vborovik.competence.core.CustomerPageType;
import ru.axbit.vborovik.competence.filtertypes.GetCustomerListFilterType;

public interface CustomerService {
    CustomerPageType getCustomer(GetCustomerListFilterType body);
}
