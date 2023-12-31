package ru.axbit.service.service;

import ru.axbit.vborovik.competence.userservice.types.v1.ActivateCustomerRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.CreateCustomerRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.DefaultResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.DeleteCustomerRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.EditCustomerRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListResponse;

/**
 * Сервис сущности заказчика {@link ru.axbit.domain.domain.user.Customer}.
 * Описание основных методов.
 */
public interface CustomerService {
    GetCustomerListResponse getCustomerList(GetCustomerListRequest body);

    DefaultResponse editCustomer(EditCustomerRequest body);

    DefaultResponse createCustomer(CreateCustomerRequest body);

    DefaultResponse deleteCustomer(DeleteCustomerRequest body);

    DefaultResponse activateCustomer(ActivateCustomerRequest body);
}
