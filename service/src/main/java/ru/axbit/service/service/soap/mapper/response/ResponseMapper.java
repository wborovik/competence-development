package ru.axbit.service.service.soap.mapper.response;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axbit.domain.domain.common.UserData;
import ru.axbit.domain.domain.order.Order;
import ru.axbit.domain.domain.user.Customer;
import ru.axbit.domain.domain.user.Executor;
import ru.axbit.vborovik.competence.core.v1.*;
import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.GetExecutorListResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.GetOrderListResponse;

import java.util.Objects;

@Service
@AllArgsConstructor
public class ResponseMapper {
    public static GetCustomerListResponse mapGetCustomerResponse(CustomerListPojo customerPojo) {
        var response = new GetCustomerListResponse();
        if (Objects.isNull(customerPojo)) return response;
        var customerPageType = CommonMapper.mapPagingResults(CustomerPageType.class, customerPojo.getCustomers());
        response.setResult(customerPageType);
        var customers = customerPojo.getCustomers();
        if (customers.isEmpty()) return response;
        var pageType = response.getResult();
        var resultList = pageType.getCustomerItem();
        customers.forEach(customer -> {
            var result = mapCustomerPageItemType(customer);
            resultList.add(result);
        });
        return response;
    }

    public static CustomerPageItemType mapCustomerPageItemType(Customer customer) {
        var result = new CustomerPageItemType();
        if(Objects.isNull(customer)) return result;
        result.setCustomerId(customer.getId());
        var userData = mapUserDataType(customer);
        result.setUserData(userData);
        return result;
    }

    public static GetExecutorListResponse mapGetExecutorResponse(ExecutorListPojo executorPojo) {
        var response = new GetExecutorListResponse();
        if (Objects.isNull(executorPojo)) return response;
        var executorPageType = CommonMapper.mapPagingResults(ExecutorPageType.class, executorPojo.getExecutors());
        response.setResult(executorPageType);
        var executors = executorPojo.getExecutors();
        if (executors.isEmpty()) return response;
        var pageType = response.getResult();
        var resultList = pageType.getCustomerItem();
        executors.forEach(executor -> {
            var result = mapExecutorPageItemType(executor);
            resultList.add(result);
        });
        return response;
    }

    public static ExecutorPageItemType mapExecutorPageItemType(Executor executor) {
        var result = new ExecutorPageItemType();
        if (Objects.isNull(executor)) return result;
        result.setExecutorId(executor.getId());
        var userData = mapUserDataType(executor);
        result.setUserData(userData);
        return result;
    }

    public static UserDataPageType mapUserDataType(UserData userData) {
        var userDataType = new UserDataPageType();
        userDataType.setName(userData.getName());
        userDataType.setSurname(userData.getSurname());
        userDataType.setAge(userData.getAge());
        userDataType = (Objects.isNull(userDataType.getName())
                && Objects.isNull(userDataType.getSurname()) && Objects.isNull(userDataType.getAge())) ?
                null : userDataType;
        return userDataType;
    }

    public static GetOrderListResponse mapGetOrderResponse(OrderListPojo orderPojo) {
        var response = new GetOrderListResponse();
        if (Objects.isNull(orderPojo)) return response;
        var orderPageType = CommonMapper.mapPagingResults(OrderPageType.class, orderPojo.getOrders());
        response.setResult(orderPageType);
        var orders = orderPojo.getOrders();
        if (orders.isEmpty()) return response;
        var pageType = response.getResult();
        var resultList = pageType.getOrderItem();
        orders.forEach(order -> {
            var result = mapOrderPageItemType(order);
            resultList.add(result);
        });
        return response;
    }

    public static OrderPageItemType mapOrderPageItemType(Order order) {
        var result = new OrderPageItemType();
        if (Objects.isNull(order)) return result;
        result.setOrderId(order.getId());
        result.setCustomer(mapCustomerPageItemType(order.getCustomer()));
        result.setExecutor(mapExecutorPageItemType(order.getExecutor()));
        return result;
    }
}
