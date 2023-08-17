package ru.axbit.service.service.soap.mapper.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import ru.axbit.domain.domain.user.Customer;

@Getter
@Builder
public class CustomerListPojo {
    private final Page<Customer> customers;
}
