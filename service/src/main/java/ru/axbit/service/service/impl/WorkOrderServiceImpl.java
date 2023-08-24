package ru.axbit.service.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.axbit.domain.domain.common.AbstractEntity;
import ru.axbit.domain.domain.order.WorkOrder;
import ru.axbit.domain.repository.CustomerRepository;
import ru.axbit.domain.repository.ExecutorRepository;
import ru.axbit.domain.repository.WorkOrderRepository;
import ru.axbit.service.service.WorkOrderService;
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
import java.time.LocalDateTime;
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

    @Override
    public GetOrderListResponse getOrderList(GetOrderListRequest body) {
        var orderPojo = getOrderList(body.getFilter(), body.getPagingOptions());

        return ResponseMapper.mapGetOrderResponse(orderPojo);
    }

    /**
     * Метод для получения списка заказов {@link WorkOrder}.
     *
     * @param filter принимает тип {@link GetOrderListFilterType}, содержащий критерии поиска.
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

    @Override
    public DefaultResponse editOrder(EditOrderRequest body) {
        var editOrderReq = body.getEditOrder();
        var orderId = editOrderReq.getId();
        var orderOptional = workOrderRepository.findByIdAndDeletedIsFalse(orderId);
        if (orderOptional.isPresent()) {
            var order = orderOptional.get();
            Optional.ofNullable(editOrderReq.getTitle())
                    .ifPresent(order::setTitle);
            Optional.ofNullable(editOrderReq.getCustomerId())
                    .flatMap(customerRepository::findById).ifPresent(order::setCustomer);
            Optional.ofNullable(editOrderReq.getExecutorId())
                    .flatMap(executorRepository::findById).ifPresent(order::setExecutor);
            order.setChanged(LocalDateTime.now());
            workOrderRepository.save(order);
        }
        return ResponseMapper.mapDefaultResponse(true);
    }
}
