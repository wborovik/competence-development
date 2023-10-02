package ru.axbit.service.service;

import ru.axbit.vborovik.competence.userservice.types.v1.ActivateOrderRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.CreateOrderRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.DefaultResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.DeleteOrderRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.EditOrderRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetExecutorByOrderSettingsRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetExecutorByOrderSettingsResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.GetOrderListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetOrderListResponse;

/**
 * Сервис сущности заказа {@link ru.axbit.domain.domain.order.WorkOrder}.
 * Описание основных методов.
 */
public interface WorkOrderService {
    GetOrderListResponse getOrderList(GetOrderListRequest body);

    DefaultResponse editOrder(EditOrderRequest body);

    DefaultResponse createOrder(CreateOrderRequest body);

    DefaultResponse deleteOrder(DeleteOrderRequest body);

    DefaultResponse activateOrder(ActivateOrderRequest body);

    GetExecutorByOrderSettingsResponse getExecutorByOrderSettings(GetExecutorByOrderSettingsRequest body);
}
