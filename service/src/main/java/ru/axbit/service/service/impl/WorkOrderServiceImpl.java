package ru.axbit.service.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.axbit.domain.domain.common.AbstractEntity;
import ru.axbit.domain.domain.order.WorkOrder;
import ru.axbit.domain.repository.ClsOrderCategoryRepository;
import ru.axbit.domain.repository.CustomerRepository;
import ru.axbit.domain.repository.ExecutorRepository;
import ru.axbit.domain.repository.WorkOrderRepository;
import ru.axbit.service.service.WorkOrderService;
import ru.axbit.service.service.common.AbstractCommonService;
import ru.axbit.service.service.json.JsonMappingService;
import ru.axbit.service.service.soap.mapper.request.CommonMapperDTO;
import ru.axbit.service.service.soap.mapper.response.OrderListPojo;
import ru.axbit.service.service.soap.mapper.response.ResponseMapper;
import ru.axbit.service.service.soap.spec.OrderSpecification;
import ru.axbit.service.util.PagingUtils;
import ru.axbit.service.util.TableNameConst;
import ru.axbit.service.util.ValidationUtils;
import ru.axbit.vborovik.competence.core.v1.PagingOptions;
import ru.axbit.vborovik.competence.filtertypes.v1.CreateOrEditOrderDataType;
import ru.axbit.vborovik.competence.filtertypes.v1.GetOrderListFilterType;
import ru.axbit.vborovik.competence.userservice.types.v1.ActivateOrderRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.CreateOrderRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.DefaultResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.DeleteOrderRequest;
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
public class WorkOrderServiceImpl extends AbstractCommonService implements WorkOrderService {

    private final WorkOrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ExecutorRepository executorRepository;
    private final ClsOrderCategoryRepository categoryRepository;
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
        Page<WorkOrder> orders = orderRepository.findAll(specification, pageRequest);

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
        var order = findEntityById(orderId, orderRepository, TableNameConst.ORDER_TABLE_NAME);
        ValidationUtils.checkIsDeleted(order, orderId, TableNameConst.ORDER_TABLE_NAME);
        createOrEditOrderDataType(order, editOrderReq.getCreateOrEditOrder());

        return ResponseMapper.mapDefaultResponse(true);
    }

    @Override
    public DefaultResponse createOrder(CreateOrderRequest body) {
        var createOrderReq = body.getCreateOrder();
        var order = new WorkOrder();
        createOrEditOrderDataType(order, createOrderReq.getCreateOrEditOrder());

        return ResponseMapper.mapDefaultResponse(true);
    }

    /**
     * Метод для удаления заказа {@link WorkOrder}.
     *
     * @param body принимает SOAP тип {@link DeleteOrderRequest}, в котором указан идентификатор записи.
     * @return возвращает SOAP тип {@link DefaultResponse}, содержащий статус проведенной операции.
     */
    @Override
    public DefaultResponse deleteOrder(DeleteOrderRequest body) {
        var deleteOrderReq = body.getDeleteOrder();
        var orderId = deleteOrderReq.getId();
        var order = findEntityById(orderId, orderRepository, TableNameConst.ORDER_TABLE_NAME);
        deleteEntity(order, orderId, TableNameConst.ORDER_TABLE_NAME);

        return ResponseMapper.mapDefaultResponse(true);
    }

    /**
     * Метод для активации удаленного заказа {@link WorkOrder}.
     *
     * @param body принимает SOAP тип {@link ActivateOrderRequest}, в котором указан идентификатор записи.
     * @return возвращает SOAP тип {@link DefaultResponse}, содержащий статус проведенной операции.
     */
    @Override
    public DefaultResponse activateOrder(ActivateOrderRequest body) {
        var activateOrderReq = body.getActivateOrder();
        var orderId = activateOrderReq.getId();
        var order = findEntityById(orderId, orderRepository, TableNameConst.ORDER_TABLE_NAME);
        activateEntity(order, orderId, TableNameConst.ORDER_TABLE_NAME);

        return ResponseMapper.mapDefaultResponse(true);
    }

    /**
     * Метод используется для создания или изменения данных сущности заказа {@link WorkOrder}.
     *
     * @param order                     передается сущность заказа {@link WorkOrder}.
     * @param createOrEditOrderDataType передается SOAP тип {@link CreateOrEditOrderDataType} с данными.
     */
    private void createOrEditOrderDataType(WorkOrder order, CreateOrEditOrderDataType createOrEditOrderDataType) {
        Optional.ofNullable(createOrEditOrderDataType.getTitle()).ifPresent(order::setTitle);
        var customerId = createOrEditOrderDataType.getCustomerId();
        if (Objects.nonNull(customerId)) {
            var customer = findEntityById(customerId, customerRepository, TableNameConst.CUSTOMER_TABLE_NAME);
            ValidationUtils.checkIsDeleted(customer, customerId, TableNameConst.CUSTOMER_TABLE_NAME);
            order.setCustomer(customer);
        }
        var executorId = createOrEditOrderDataType.getExecutorId();
        if (Objects.nonNull(executorId)) {
            var executor = findEntityById(executorId, executorRepository, TableNameConst.EXECUTOR_TABLE_NAME);
            ValidationUtils.checkIsDeleted(executor, executorId, TableNameConst.EXECUTOR_TABLE_NAME);
            order.setExecutor(executor);
        }
        var categoryId = createOrEditOrderDataType.getCategoryId();
        if (Objects.nonNull(categoryId)) {
            var category = findEntityById(categoryId, categoryRepository, TableNameConst.CATEGORY_TABLE_NAME);
            ValidationUtils.checkIsDeleted(category, categoryId, TableNameConst.CATEGORY_TABLE_NAME);
            order.setCategory(category);
        }
        Optional.ofNullable(createOrEditOrderDataType.getOrderData()).ifPresent(orderData
                -> order.setOrderCheck(jsonMappingService.mapToJsonNode(orderData)));

        orderRepository.save(order);
    }
}
