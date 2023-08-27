package ru.axbit.service.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.axbit.domain.domain.common.AbstractEntity;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.order.WorkOrder;
import ru.axbit.domain.domain.user.Customer;
import ru.axbit.domain.domain.user.Executor;
import ru.axbit.domain.repository.CustomerRepository;
import ru.axbit.domain.repository.ExecutorRepository;
import ru.axbit.domain.repository.WorkOrderRepository;
import ru.axbit.service.exception.BusinessException;
import ru.axbit.service.exception.BusinessExceptionEnum;
import ru.axbit.service.service.WorkOrderService;
import ru.axbit.service.service.json.JsonMappingService;
import ru.axbit.service.service.soap.mapper.request.CommonMapperDTO;
import ru.axbit.service.service.soap.mapper.response.OrderListPojo;
import ru.axbit.service.service.soap.mapper.response.ResponseMapper;
import ru.axbit.service.service.soap.spec.OrderSpecification;
import ru.axbit.service.util.PagingUtils;
import ru.axbit.vborovik.competence.core.v1.PagingOptions;
import ru.axbit.vborovik.competence.filtertypes.v1.GetOrderListFilterType;
import ru.axbit.vborovik.competence.userservice.types.v1.DefaultResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.EditOrderRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetOrderListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetOrderListResponse;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

/**
 * Реализация основных CRUD методов сущности заказа {@link WorkOrder}.
 */
@Service
@Transactional
@AllArgsConstructor
public class WorkOrderServiceImpl implements WorkOrderService {
    private final WorkOrderRepository workOrderRepository;
    private final CustomerRepository customerRepository;
    private final ExecutorRepository executorRepository;
    private final JsonMappingService jsonMappingService;

    @Override
    public GetOrderListResponse getOrderList(GetOrderListRequest body) {
        var orderPojo = getOrderList(body.getFilter(), body.getPagingOptions());

        return ResponseMapper.mapGetOrderResponse(orderPojo);
    }

    /**
     * Метод для получения списка заказов {@link WorkOrder}.
     *
     * @param filter        принимает тип {@link GetOrderListFilterType}, содержащий критерии поиска.
     * @param pagingOptions принимает тип {@link PagingOptions}, содержащий условия сортировки страниц.
     * @return Возвращает {@link OrderListPojo}, который содержит страницы заказов, полученных из БД.
     */
    private OrderListPojo getOrderList(GetOrderListFilterType filter, PagingOptions pagingOptions) {
        if (Objects.isNull(filter)) {
            return null;
        }
        var pageRequest = PagingUtils.getPageRequest(pagingOptions);
        var sorting = PagingUtils.getSortOptions(pagingOptions, AbstractEntity.Fields.id);
        var criteriaDTO = CommonMapperDTO.mapOrderDTO(filter);
        Specification<WorkOrder> specification = OrderSpecification.create(criteriaDTO, sorting);
        Page<WorkOrder> orders = workOrderRepository.findAll(specification, pageRequest);

        return OrderListPojo.builder()
                .orders(orders)
                .build();
    }

    /**
     * Метод для изменения заказа {@link WorkOrder}.
     *
     * @param body передается SOAP тип {@link EditOrderRequest} с вносимыми изменениями.
     * @return возвращается SOAP тип {@link DefaultResponse}, содержащий статус проведенной операции.
     */
    @Override
    public DefaultResponse editOrder(EditOrderRequest body) {
        var editOrderReq = body.getEditOrder();
        var orderId = editOrderReq.getId();
        var orderOptional = workOrderRepository.findById(orderId);
        orderOptional.filter(AuditEntity::isDeleted)
                .ifPresent(order -> BusinessExceptionEnum.E002.thr(order.getId(), WorkOrder.class.getSimpleName()));
        if (orderOptional.isPresent()) {
            var order = orderOptional.get();
            Optional.ofNullable(editOrderReq.getTitle()).ifPresent(order::setTitle);
            var customerId = editOrderReq.getCustomerId();
            if (Objects.nonNull(customerId)) {
                var customer = Optional.of(customerId)
                        .flatMap(customerRepository::findById)
                        .orElseThrow(() -> new BusinessException(BusinessExceptionEnum.E001, customerId,
                                Customer.class.getSimpleName()));
                order.setCustomer(customer);
            }
            var executorId = editOrderReq.getExecutorId();
            if (Objects.nonNull(executorId)) {
                var executor = Optional.ofNullable(editOrderReq.getExecutorId())
                        .flatMap(executorRepository::findById)
                        .orElseThrow(() -> new BusinessException(BusinessExceptionEnum.E001, executorId,
                                Executor.class.getSimpleName()));
                order.setExecutor(executor);
            }
            Optional.ofNullable(editOrderReq.getOrderData()).ifPresent(orderData
                    -> order.setOrderCheck(jsonMappingService.mapToJsonNode(orderData)));
            workOrderRepository.save(order);
        } else {
            BusinessExceptionEnum.E001.thr(orderId, WorkOrder.class.getSimpleName());
        }
        return ResponseMapper.mapDefaultResponse(true);
    }
}
