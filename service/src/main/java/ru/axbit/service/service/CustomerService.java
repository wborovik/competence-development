package ru.axbit.service.service;

import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListResponse;

public interface CustomerService {
    GetCustomerListResponse getCustomerList(GetCustomerListRequest body);
}
