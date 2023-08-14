package ru.axbit.service.service.soap.mapper.response;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axbit.domain.domain.user.Customer;
import ru.axbit.service.util.SafeUtils;
import ru.axbit.vborovik.competence.core.CustomerPageType;
import ru.axbit.vborovik.competence.myservice.types.GetCustomerListResponse;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class ResponseMapper {
    public static GetCustomerListResponse mapGetCustomerResponse(Set<Customer> customers) {
        var response = new GetCustomerListResponse();
        if (customers.isEmpty()) return response;
        List<CustomerPageType> resultList = response.getResult();
        customers.forEach(customer -> {
            var result = new CustomerPageType();
            result.setCustomerId(customer.getId());
            var userData = result.getUserData();
            userData.setName(SafeUtils.safeGet(() -> customer.getUserData().getName()));
            userData.setSurname(SafeUtils.safeGet(() -> customer.getUserData().getSurname()));
            userData.setAge(SafeUtils.safeGet(() -> customer.getUserData().getAge()));
            resultList.add(result);
        });
        return response;
    }
}
