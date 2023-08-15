package ru.axbit.service.service.soap.mapper.response;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axbit.domain.domain.common.UserData;
import ru.axbit.domain.domain.user.Customer;
import ru.axbit.vborovik.competence.core.CustomerPageType;
import ru.axbit.vborovik.competence.core.UserDataPageType;
import ru.axbit.vborovik.competence.userservice.types.GetCustomerListResponse;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class ResponseMapper {
    public static GetCustomerListResponse mapGetCustomerResponse(Set<Customer> customers) {
        var response = new GetCustomerListResponse();
        if (customers.isEmpty()) return response;
        var resultList = response.getResult();
        customers.forEach(customer -> {
            var result = new CustomerPageType();
            result.setCustomerId(customer.getId());
            var userData = new UserDataPageType();
            var customerUserData = Optional.ofNullable(customer.getUserData());
            customerUserData.map(UserData::getName).ifPresent(userData::setName);
            customerUserData.map(UserData::getSurname).ifPresent(userData::setSurname);
            customerUserData.map(UserData::getAge).ifPresent(userData::setAge);
            if (Objects.isNull(userData.getName())
                    && Objects.isNull(userData.getSurname()) && Objects.isNull(userData.getAge())) {
                result.setUserData(null);
            } else {
                result.setUserData(userData);
            }
            resultList.add(result);
        });
        return response;
    }
}
