package ru.axbit.service.service.soap;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axbit.service.service.CustomerService;
import ru.axbit.vborovik.competence.userservice.UserServicePortType;
import ru.axbit.vborovik.competence.userservice.types.GetCustomerListRequest;
import ru.axbit.vborovik.competence.userservice.types.GetCustomerListResponse;
import ru.axbit.vborovik.competence.userservice.types.GetExecutorListRequest;
import ru.axbit.vborovik.competence.userservice.types.GetExecutorListResponse;

@Service
@AllArgsConstructor
public class UserServiceSoap implements UserServicePortType {
    private final CustomerService customerService;

    @Override
    public GetExecutorListResponse getExecutorList(GetExecutorListRequest body) {
        return null;
    }

    @Override
    public GetCustomerListResponse getCustomerList(GetCustomerListRequest body) {
        return customerService.getCustomerList(body);
    }
}
