package ru.axbit.service.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.axbit.domain.domain.user.Customer;
import ru.axbit.domain.repository.CustomerRepository;
import ru.axbit.service.service.CustomerService;
import ru.axbit.service.service.soap.mapper.response.ResponseMapper;
import ru.axbit.vborovik.competence.filtertypes.v1.GetCustomerListFilterType;
import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListResponse;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public GetCustomerListResponse getCustomerList(GetCustomerListRequest body) {
        var customers = getCustomer(body.getFilter());

        return ResponseMapper.mapGetCustomerResponse(customers);
    }

    private Set<Customer> getCustomer(GetCustomerListFilterType filter) {
        if (Objects.isNull(filter)) return new HashSet<>();
        Set<Long> customerIds = new HashSet<>(filter.getCustomerId());

        return customerRepository.findAllByIdInAndDeletedIsFalseOrderById(customerIds);
    }
}
