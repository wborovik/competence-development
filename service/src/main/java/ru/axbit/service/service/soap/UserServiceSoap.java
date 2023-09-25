package ru.axbit.service.service.soap;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axbit.service.service.BillService;
import ru.axbit.service.service.CustomerService;
import ru.axbit.service.service.EvaluationService;
import ru.axbit.service.service.ExecutorService;
import ru.axbit.service.service.WorkOrderService;
import ru.axbit.service.util.CommonResultBuilder;
import ru.axbit.vborovik.competence.userservice.types.v1.ActivateCustomerRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.ActivateExecutorRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.ActivateOrderRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.CreateBillRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.CreateCustomerRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.CreateExecutorRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.CreateOrderRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.DefaultResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.DeleteCustomerRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.DeleteExecutorRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.DeleteOrderRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.EditCustomerRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.EditEvaluationRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.EditExecutorRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.EditOrderRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.GetExecutorListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetExecutorListResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.GetOrderListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetOrderListResponse;
import ru.axbit.vborovik.competence.userservice.v1.UserServicePortType;

/**
 * Класс, реализующий методы описанные в wsdl схеме UserService.
 */
@Service
@AllArgsConstructor
public class UserServiceSoap implements UserServicePortType {
    private final CustomerService customerService;
    private final ExecutorService executorService;
    private final WorkOrderService orderService;
    private final EvaluationService evaluationService;
    private final BillService billService;

    @Override
    public DefaultResponse deleteOrder(DeleteOrderRequest body) {
        return CommonResultBuilder.buildResponse(orderService::deleteOrder, body);
    }

    @Override
    public DefaultResponse activateCustomer(ActivateCustomerRequest body) {
        return CommonResultBuilder.buildResponse(customerService::activateCustomer, body);
    }

    @Override
    public GetExecutorListResponse getExecutorList(GetExecutorListRequest body) {
        return CommonResultBuilder.buildResponse(executorService::getExecutorList, body);
    }

    @Override
    public DefaultResponse deleteExecutor(DeleteExecutorRequest body) {
        return CommonResultBuilder.buildResponse(executorService::deleteExecutor, body);
    }

    @Override
    public DefaultResponse createBill(CreateBillRequest body) {
        return CommonResultBuilder.buildResponse(billService::createBill, body);
    }

    @Override
    public GetOrderListResponse getOrderList(GetOrderListRequest body) {
        return CommonResultBuilder.buildResponse(orderService::getOrderList, body);
    }

    @Override
    public DefaultResponse createCustomer(CreateCustomerRequest body) {
        return CommonResultBuilder.buildResponse(customerService::createCustomer, body);
    }

    @Override
    public DefaultResponse createExecutor(CreateExecutorRequest body) {
        return CommonResultBuilder.buildResponse(executorService::createExecutor, body);
    }

    @Override
    public DefaultResponse deleteCustomer(DeleteCustomerRequest body) {
        return CommonResultBuilder.buildResponse(customerService::deleteCustomer, body);
    }

    @Override
    public GetCustomerListResponse getCustomerList(GetCustomerListRequest body) {
        return CommonResultBuilder.buildResponse(customerService::getCustomerList, body);
    }

    @Override
    public DefaultResponse activateExecutor(ActivateExecutorRequest body) {
        return CommonResultBuilder.buildResponse(executorService::activateExecutor, body);
    }

    @Override
    public DefaultResponse activateOrder(ActivateOrderRequest body) {
        return CommonResultBuilder.buildResponse(orderService::activateOrder, body);
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
    public DefaultResponse editEvaluation(EditEvaluationRequest body) {
        return CommonResultBuilder.buildResponse(evaluationService::editEvaluation, body);
    }

    @Override
    public DefaultResponse editOrder(EditOrderRequest body) {
        return CommonResultBuilder.buildResponse(orderService::editOrder, body);
    }

    @Override
    public DefaultResponse createOrder(CreateOrderRequest body) {
        return CommonResultBuilder.buildResponse(orderService::createOrder, body);
    }
}
