package ru.axbit.service.service.soap;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axbit.service.service.CustomerService;
import ru.axbit.vborovik.competence.myservice.MyServicePortType;
import ru.axbit.vborovik.competence.myservice.types.GetCustomerListRequest;
import ru.axbit.vborovik.competence.myservice.types.GetCustomerListResponse;

@Service
@AllArgsConstructor
public class MyServiceSoap implements MyServicePortType {
    private final CustomerService customerService;

    @Override
    public void getExecutorList() {
    }

    @Override
    public GetCustomerListResponse getCustomerList(GetCustomerListRequest body) {
        return customerService.getCustomerList(body);
    }
}
