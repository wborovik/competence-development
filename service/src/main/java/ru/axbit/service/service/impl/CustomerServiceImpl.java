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
import ru.axbit.service.service.common.AbstractCommonService;
import ru.axbit.service.service.soap.mapper.request.CommonMapperDTO;
import ru.axbit.service.service.soap.mapper.response.CustomerListPojo;
import ru.axbit.service.service.soap.mapper.response.ResponseMapper;
import ru.axbit.service.service.soap.spec.CustomerSpecification;
import ru.axbit.service.util.PagingUtils;
import ru.axbit.service.util.ValidationUtils;
import ru.axbit.vborovik.competence.core.v1.PagingOptions;
import ru.axbit.vborovik.competence.filtertypes.v1.GetCustomerListFilterType;
import ru.axbit.vborovik.competence.userservice.types.v1.ActivateCustomerRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.CreateCustomerRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.DefaultResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.DeleteCustomerRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.EditCustomerRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListResponse;

import java.util.Objects;

/**
 * Реализация основных CRUD методов сущности заказчика {@link Customer}.
 */
@Service
@Transactional
@AllArgsConstructor
public class CustomerServiceImpl extends AbstractCommonService implements CustomerService {
    private final CustomerRepository customerRepository;

    private static final String CUSTOMER_TABLE_NAME = Customer.class.getSimpleName();

    @Override
    public GetCustomerListResponse getCustomerList(GetCustomerListRequest body) {
        var customerPojo = getCustomerList(body.getFilter(), body.getPagingOptions());
        return ResponseMapper.mapGetCustomerResponse(customerPojo);
    }

    /**
     * Метод для получения списка заказчиков {@link Customer}.
     *
     * @param filter        принимает тип {@link GetCustomerListFilterType}, содержащий критерии поиска.
     * @param pagingOptions принимает тип {@link PagingOptions}, содержащий условия сортировки страниц.
     * @return возвращает {@link CustomerListPojo}, который содержит страницы заказчиков, полученных из БД.
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

    /**
     * Метод для изменения клиента {@link Customer}.
     *
     * @param body принимает SOAP тип {@link EditCustomerRequest} с вносимыми изменениями.
     * @return возвращает SOAP тип {@link DefaultResponse}, содержащий статус проведенной операции.
     */
    @Override
    public DefaultResponse editCustomer(EditCustomerRequest body) {
        var editCustomerReq = body.getEditCustomer();
        var customerId = editCustomerReq.getId();
        var customer = findEntityById(customerId, customerRepository, CUSTOMER_TABLE_NAME);
        ValidationUtils.checkIsDeleted(customer, customerId, CUSTOMER_TABLE_NAME);
        setUserData(customer, customerRepository, editCustomerReq.getUserData());

        return ResponseMapper.mapDefaultResponse(true);
    }

    /**
     * Метод для создания клиента {@link Customer}.
     *
     * @param body принимает SOAP тип {@link CreateCustomerRequest} с сохраняемыми данными.
     * @return возвращает SOAP тип {@link DefaultResponse}, содержащий статус проведенной операции.
     */
    @Override
    public DefaultResponse createCustomer(CreateCustomerRequest body) {
        var createCustomerReq = body.getCreateCustomer();
        var customer = new Customer();
        setUserData(customer, customerRepository, createCustomerReq.getUserData());

        return ResponseMapper.mapDefaultResponse(true);
    }

    /**
     * Метод для удаления клиента {@link Customer}.
     *
     * @param body принимает SOAP тип {@link DeleteCustomerRequest}, в котором указан идентификатор записи.
     * @return возвращает SOAP тип {@link DefaultResponse}, содержащий статус проведенной операции.
     */
    @Override
    public DefaultResponse deleteCustomer(DeleteCustomerRequest body) {
        var deleteCustomerReq = body.getDeleteCustomer();
        var customerId = deleteCustomerReq.getId();
        var customer = findEntityById(customerId, customerRepository, CUSTOMER_TABLE_NAME);
        deleteEntity(customer, customerId, CUSTOMER_TABLE_NAME);

        return ResponseMapper.mapDefaultResponse(true);
    }

    /**
     * Метод для активации удаленного клиента {@link Customer}.
     *
     * @param body принимает SOAP тип {@link ActivateCustomerRequest}, в котором указан идентификатор записи.
     * @return возвращает SOAP тип {@link DefaultResponse}, содержащий статус проведенной операции.
     */
    @Override
    public DefaultResponse activateCustomer(ActivateCustomerRequest body) {
        var activateCustomerReq = body.getActivateCustomer();
        var customerId = activateCustomerReq.getId();
        var customer = findEntityById(customerId, customerRepository, CUSTOMER_TABLE_NAME);
        activateEntity(customer, customerId, CUSTOMER_TABLE_NAME);

        return ResponseMapper.mapDefaultResponse(true);
    }
}
