package ru.axbit.service.service.soap.mapper.response;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axbit.domain.domain.common.UserData;
import ru.axbit.domain.domain.user.Executor;
import ru.axbit.vborovik.competence.core.v1.CustomerPageItemType;
import ru.axbit.vborovik.competence.core.v1.CustomerPageType;
import ru.axbit.vborovik.competence.core.v1.ExecutorPageType;
import ru.axbit.vborovik.competence.core.v1.UserDataPageType;
import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.GetExecutorListResponse;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class ResponseMapper {
    public static GetCustomerListResponse mapGetCustomerResponse(CustomerListPojo customerPojo) {
        var response = new GetCustomerListResponse();
        CustomerPageType customerPageType = CommonMapper.mapPagingResults(CustomerPageType.class, customerPojo.getCustomers());
        response.setResult(customerPageType);
        var customers = customerPojo.getCustomers();
        if (customers.isEmpty()) return response;
        var pageType = response.getResult();
        var resultList = pageType.getCustomerItem();
        customers.forEach(customer -> {
            var result = new CustomerPageItemType();
            result.setCustomerId(customer.getId());
            var customerUserData = Optional.ofNullable(customer.getUserData());
            var userData = mapExecutorPageType(customerUserData);
            result.setUserData(userData);
            resultList.add(result);
        });
        return response;
    }

    public static GetExecutorListResponse mapGetExecutorResponse(Set<Executor> executors) {
        var response = new GetExecutorListResponse();
        if (executors.isEmpty()) return response;
        var resultList = response.getResult();
        executors.forEach(executor -> {
            var result = new ExecutorPageType();
            result.setExecutorId(executor.getId());
            var executorUserData = Optional.ofNullable(executor.getUserData());
            var userData = mapExecutorPageType(executorUserData);
            result.setUserData(userData);
            resultList.add(result);
        });
        return response;
    }

    public static UserDataPageType mapExecutorPageType(Optional<UserData> userData) {
        var userDataType = new UserDataPageType();
        userData.map(UserData::getName).ifPresent(userDataType::setName);
        userData.map(UserData::getSurname).ifPresent(userDataType::setSurname);
        userData.map(UserData::getAge).ifPresent(userDataType::setAge);
        userDataType = (Objects.isNull(userDataType.getName())
                && Objects.isNull(userDataType.getSurname()) && Objects.isNull(userDataType.getAge())) ?
                null : userDataType;
        return userDataType;
    }
}
