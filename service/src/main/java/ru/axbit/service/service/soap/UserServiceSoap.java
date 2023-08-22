package ru.axbit.service.service.soap;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axbit.service.service.CustomerService;
import ru.axbit.service.service.ExecutorService;
import ru.axbit.service.service.WorkOrderService;
import ru.axbit.service.util.CommonResultBuilder;
import ru.axbit.vborovik.competence.userservice.types.v1.*;
import ru.axbit.vborovik.competence.userservice.v1.UserServicePortType;

@Service
@AllArgsConstructor
public class UserServiceSoap implements UserServicePortType {
    private final CustomerService customerService;
    private final ExecutorService executorService;
    private final WorkOrderService orderService;

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

    @Override
    public DefaultResponse editCustomer(EditCustomerRequest body) {
        return CommonResultBuilder.buildResponse(customerService::editCustomer, body);
    }

    @Override
    public DefaultResponse editExecutor(EditExecutorRequest body) {
        return CommonResultBuilder.buildResponse(executorService::editExecutor, body);
    }

    @Override
    public DefaultResponse editOrder(EditOrderRequest body) {
        return CommonResultBuilder.buildResponse(orderService::editOrder, body);
    }
}
