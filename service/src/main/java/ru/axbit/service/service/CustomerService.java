package ru.axbit.service.service;

import ru.axbit.vborovik.competence.userservice.types.GetCustomerListRequest;
import ru.axbit.vborovik.competence.userservice.types.GetCustomerListResponse;

public interface CustomerService {
    GetCustomerListResponse getCustomerList(GetCustomerListRequest body);
}
