package ru.axbit.service.service.soap;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axbit.service.service.CustomerService;
import ru.axbit.service.service.ExecutorService;
import ru.axbit.service.util.CommonResultBuilder;
import ru.axbit.vborovik.competence.userservice.v1.UserServicePortType;
import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.GetExecutorListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetExecutorListResponse;

@Service
@AllArgsConstructor
public class UserServiceSoap implements UserServicePortType {
    private final CustomerService customerService;
    private final ExecutorService executorService;

    @Override
    public GetExecutorListResponse getExecutorList(GetExecutorListRequest body) {
        return CommonResultBuilder.buildResponse(executorService::getExecutorList, body);
    }

    @Override
    public GetCustomerListResponse getCustomerList(GetCustomerListRequest body) {
        return CommonResultBuilder.buildResponse(customerService::getCustomerList, body);
    }
}
