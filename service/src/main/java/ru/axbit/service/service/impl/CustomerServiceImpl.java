package ru.axbit.service.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.axbit.domain.domain.common.AbstractEntity;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.user.Customer;
import ru.axbit.domain.repository.CustomerRepository;
import ru.axbit.service.exception.BusinessExceptionEnum;
import ru.axbit.service.service.CustomerService;
import ru.axbit.service.service.soap.mapper.request.CommonMapperDTO;
import ru.axbit.service.service.soap.mapper.response.CustomerListPojo;
import ru.axbit.service.service.soap.mapper.response.ResponseMapper;
import ru.axbit.service.service.soap.spec.CustomerSpecification;
import ru.axbit.service.util.PagingUtils;
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
        var customerOptional = customerRepository.findById(customerId);
        customerOptional.filter(AuditEntity::isDeleted)
                .ifPresent(customer -> BusinessExceptionEnum.E002
                        .thr(customer.getId(), Customer.class.getSimpleName()));
        if (customerOptional.isPresent()) {
            var customer = customerOptional.get();
            Optional.ofNullable(editCustomerReq.getCustomerName()).ifPresent(customer::setName);
            Optional.ofNullable(editCustomerReq.getCustomerSurname()).ifPresent(customer::setSurname);
            Optional.ofNullable(editCustomerReq.getCustomerAge()).ifPresent(customer::setAge);
            customerRepository.save(customer);
        } else {
            BusinessExceptionEnum.E001.thr(customerId, Customer.class.getSimpleName());
        }
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
        Optional.ofNullable(createCustomerReq.getCustomerName()).ifPresent(customer::setName);
        Optional.ofNullable(createCustomerReq.getCustomerSurname()).ifPresent(customer::setSurname);
        Optional.ofNullable(createCustomerReq.getCustomerAge()).ifPresent(customer::setAge);
        customerRepository.save(customer);

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
        var customerOptional = customerRepository.findById(customerId);
        customerOptional.filter(AuditEntity::isDeleted)
                .ifPresent(customer -> BusinessExceptionEnum.E002
                        .thr(customer.getId(), Customer.class.getSimpleName()));
        deleteEntity(customerOptional, customerId, Customer.class.getSimpleName());

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
        var customerOptional = customerRepository.findById(customerId);
        customerOptional.filter(AuditEntity::nonDeleted)
                .ifPresent(customer -> BusinessExceptionEnum.E005
                        .thr(customer.getId(), Customer.class.getSimpleName()));
        activateEntity(customerOptional, customerId, Customer.class.getSimpleName());

        return ResponseMapper.mapDefaultResponse(true);
    }

    /**
     * Вспомогательный метод, который делает проверку существует ли в БД запись по переданному id.
     * Если существует, для записи меняется статус на "удалена".
     * Если не существует, будет выброшено соответствующее бизнес исключение.
     *
     * @param optionalAudit передаются результаты поиска в БД, обернутые типом {@link Optional}.
     * @param entityId      передается идентификатор, по которому производилась запись типа {@link Long}.
     * @param simpleName    передается имя таблицы, в которой осуществлялся поиск типа {@link String}.
     */
    public static void deleteEntity(Optional<? extends AuditEntity> optionalAudit, Long entityId, String simpleName) {
        if (optionalAudit.isPresent()) {
            var entity = optionalAudit.get();
            entity.setDeleted(true);
        } else {
            BusinessExceptionEnum.E001.thr(entityId, simpleName);
        }
    }

    /**
     * Вспомогательный метод, который делает проверку существует ли в БД запись по переданному id.
     * Если существует, для записи меняется статус на "активна".
     * Если не существует, будет выброшено соответствующее бизнес исключение.
     *
     * @param optionalAudit передаются результаты поиска в БД, обернутые типом {@link Optional}.
     * @param entityId      передается идентификатор, по которому производилась запись типа {@link Long}.
     * @param simpleName    передается имя таблицы, в которой осуществлялся поиск типа {@link String}.
     */
    public static void activateEntity(Optional<? extends AuditEntity> optionalAudit, Long entityId, String simpleName) {
        if (optionalAudit.isPresent()) {
            var entity = optionalAudit.get();
            entity.activate();
        } else {
            BusinessExceptionEnum.E001.thr(entityId, simpleName);
        }
    }
}
