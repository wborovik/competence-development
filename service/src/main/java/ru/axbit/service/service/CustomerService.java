package ru.axbit.service.service;

import ru.axbit.vborovik.competence.myservice.types.GetCustomerListRequest;
import ru.axbit.vborovik.competence.myservice.types.GetCustomerListResponse;

public interface CustomerService {
    GetCustomerListResponse getCustomerList(GetCustomerListRequest body);
}
