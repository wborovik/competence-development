package ru.axbit.service.service.soap;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axbit.service.service.CustomerService;
import ru.axbit.service.service.ExecutorService;
import ru.axbit.service.service.OrderService;
import ru.axbit.service.util.CommonResultBuilder;
import ru.axbit.vborovik.competence.userservice.types.v1.*;
import ru.axbit.vborovik.competence.userservice.v1.UserServicePortType;

@Service
@AllArgsConstructor
public class UserServiceSoap implements UserServicePortType {
    private final CustomerService customerService;
    private final ExecutorService executorService;
    private final OrderService orderService;

    @Override
    public GetExecutorListResponse getExecutorList(GetExecutorListRequest body) {
        return CommonResultBuilder.buildResponse(executorService::getExecutorList, body);
    }

    @Override
    public GetOrderListResponse getOrderList(GetOrderListRequest body) {
        return CommonResultBuilder.buildResponse(orderService::getOrderList, body);
    }

    @Override
    public GetCustomerListResponse getCustomerList(GetCustomerListRequest body) {
        return CommonResultBuilder.buildResponse(customerService::getCustomerList, body);
    }
}
