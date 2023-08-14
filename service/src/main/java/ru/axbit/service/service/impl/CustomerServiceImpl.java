package ru.axbit.service.service.impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axbit.domain.domain.user.Customer;
import ru.axbit.domain.repository.CustomerRepository;
import ru.axbit.service.service.CustomerService;
import ru.axbit.service.service.soap.mapper.request.CustomerMapperDTO;
import ru.axbit.service.service.soap.mapper.response.ResponseMapper;
import ru.axbit.vborovik.competence.filtertypes.GetCustomerListFilterType;
import ru.axbit.vborovik.competence.myservice.types.GetCustomerListRequest;
import ru.axbit.vborovik.competence.myservice.types.GetCustomerListResponse;

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
        var criteriaDTO = CustomerMapperDTO.mapCustomerDTO(body.getFilter());
        criteriaDTO.setExcludeDeleted(true);
        var customers = getCustomer(body.getFilter());
        return ResponseMapper.mapGetCustomerResponse(customers);
    }

    private Set<Customer> getCustomer(GetCustomerListFilterType filter) {
        if (Objects.isNull(filter)) return null;
        Set<Long> customerIds = new HashSet<>(filter.getCustomerId());

        return customerRepository.findAllByIdInAndDeletedIsFalse(customerIds);
    }
}
