package ru.axbit.service.service.soap.mapper.response;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axbit.domain.domain.common.UserData;
import ru.axbit.vborovik.competence.core.v1.*;
import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.GetExecutorListResponse;

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
            var result = new CustomerPageItemType();
            result.setCustomerId(customer.getId());
            var userData = mapUserDataType(customer);
            result.setUserData(userData);
            resultList.add(result);
        });
        return response;
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
            var result = new ExecutorPageItemType();
            result.setExecutorId(executor.getId());
            var userData = mapUserDataType(executor);
            result.setUserData(userData);
            resultList.add(result);
        });
        return response;
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
}
