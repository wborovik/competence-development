package ru.axbit.service.service;

import ru.axbit.vborovik.competence.userservice.types.v1.DefaultResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.EditCustomerRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListResponse;

public interface CustomerService {
    GetCustomerListResponse getCustomerList(GetCustomerListRequest body);
    DefaultResponse editCustomer(EditCustomerRequest body);
}
