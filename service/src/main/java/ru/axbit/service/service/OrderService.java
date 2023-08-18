package ru.axbit.service.service;

import ru.axbit.vborovik.competence.userservice.types.v1.GetOrderListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetOrderListResponse;

public interface OrderService {
    GetOrderListResponse getOrderList(GetOrderListRequest body);
}
