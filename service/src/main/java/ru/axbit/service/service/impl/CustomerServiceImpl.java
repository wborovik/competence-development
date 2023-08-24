package ru.axbit.service.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.axbit.domain.domain.common.AbstractEntity;
import ru.axbit.domain.domain.user.Customer;
import ru.axbit.domain.repository.CustomerRepository;
import ru.axbit.service.service.CustomerService;
import ru.axbit.service.service.soap.mapper.request.CommonMapperDTO;
import ru.axbit.service.service.soap.mapper.response.CustomerListPojo;
import ru.axbit.service.service.soap.mapper.response.ResponseMapper;
import ru.axbit.service.service.soap.spec.CustomerSpecification;
import ru.axbit.service.util.PagingUtils;
import ru.axbit.vborovik.competence.core.v1.PagingOptions;
import ru.axbit.vborovik.competence.filtertypes.v1.GetCustomerListFilterType;
import ru.axbit.vborovik.competence.userservice.types.v1.DefaultResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.EditCustomerRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListResponse;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Реализация основных CRUD методов сущности заказчика {@link Customer}.
 */
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

    /**
     * Метод для получения списка заказчиков {@link Customer}.
     *
     * @param filter принимает тип {@link GetCustomerListFilterType}, содержащий критерии поиска.
     * @param pagingOptions принимает тип {@link PagingOptions}, содержащий условия сортировки страниц.
     * @return Возвращает {@link CustomerListPojo}, который содержит страницы заказчиков, полученных из БД.
     */
    private CustomerListPojo getCustomerList(GetCustomerListFilterType filter, PagingOptions pagingOptions) {
        if (Objects.isNull(filter)) {
            return null;
        }
        var pageRequest = PagingUtils.getPageRequest(pagingOptions);
        var sorting = PagingUtils.getSortOptions(pagingOptions, AbstractEntity.Fields.id);
        var criteriaDto = CommonMapperDTO.mapCustomerDTO(filter);
        Specification<Customer> specification = CustomerSpecification.create(criteriaDto, sorting);
        Page<Customer> customers = customerRepository.findAll(specification, pageRequest);

        return CustomerListPojo.builder()
                .customers(customers)
                .build();
    }

    @Override
    public DefaultResponse editCustomer(EditCustomerRequest body) {
        var editCustomerReq = body.getEditCustomer();
        var customerId = editCustomerReq.getId();
        var customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            var customer = customerOptional.get();
            Optional.ofNullable(editCustomerReq.getCustomerName()).ifPresent(customer::setName);
            Optional.ofNullable(editCustomerReq.getCustomerSurname()).ifPresent(customer::setSurname);
            Optional.ofNullable(editCustomerReq.getCustomerAge()).ifPresent(customer::setAge);
            customer.setChanged(LocalDateTime.now());
            customerRepository.save(customer);
        }
        return ResponseMapper.mapDefaultResponse(true);
    }
}
