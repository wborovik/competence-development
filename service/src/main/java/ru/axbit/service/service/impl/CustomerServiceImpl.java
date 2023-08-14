package ru.axbit.service.service.impl;

import org.springframework.stereotype.Service;
import ru.axbit.service.service.CustomerService;
import ru.axbit.vborovik.competence.core.CustomerPageType;
import ru.axbit.vborovik.competence.filtertypes.GetCustomerListFilterType;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Override
    public CustomerPageType getCustomer(GetCustomerListFilterType body) {
        var response = new CustomerPageType();
        var id = body.getCustomerId();

        return response;
    }
}
