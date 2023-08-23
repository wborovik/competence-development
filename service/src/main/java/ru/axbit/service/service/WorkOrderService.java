package ru.axbit.service.service;

import ru.axbit.vborovik.competence.userservice.types.v1.DefaultResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.EditOrderRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetOrderListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetOrderListResponse;

public interface WorkOrderService {
    GetOrderListResponse getOrderList(GetOrderListRequest body);
    DefaultResponse editOrder(EditOrderRequest body);
}
