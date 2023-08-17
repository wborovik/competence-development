package ru.axbit.service.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.axbit.domain.domain.user.Customer;
import ru.axbit.domain.repository.CustomerRepository;
import ru.axbit.service.service.CustomerService;
import ru.axbit.service.service.soap.dto.CustomerCriteriaDTO;
import ru.axbit.service.service.soap.mapper.response.CustomerListPojo;
import ru.axbit.service.service.soap.mapper.response.ResponseMapper;
import ru.axbit.service.util.PagingUtils;
import ru.axbit.vborovik.competence.core.v1.PagingOptions;
import ru.axbit.vborovik.competence.filtertypes.v1.GetCustomerListFilterType;
import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListResponse;
import ru.axbit.service.service.soap.spec.CustomerSpecification;

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
        var customerPojo = getCustomerList(body.getFilter(), body.getPagingOptions());
        return ResponseMapper.mapGetCustomerResponse(customerPojo);
    }

    private CustomerListPojo getCustomerList(GetCustomerListFilterType filter, PagingOptions pagingOptions) {
        if (Objects.isNull(filter)) return null;
        PageRequest pageRequest = PagingUtils.getPageRequest(pagingOptions);
        var sorting = PagingUtils.getSortOptions(pagingOptions, "id");
        var criteriaDto = new CustomerCriteriaDTO();
        criteriaDto.setCustomerId(filter.getCustomerId());
        Specification<Customer> specification = CustomerSpecification.create(criteriaDto, sorting);
        Page<Customer> customers = customerRepository.findAll(specification, pageRequest);

        return CustomerListPojo.builder()
                .customers(customers)
                .build();
    }

    private Set<Customer> getCustomer(GetCustomerListFilterType filter) {
        if (Objects.isNull(filter)) return new HashSet<>();
        Set<Long> customerIds = new HashSet<>(filter.getCustomerId());

        return customerRepository.findAllByIdInAndDeletedIsFalseOrderById(customerIds);
    }
}
