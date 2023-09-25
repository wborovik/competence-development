package ru.axbit.service.service;

import ru.axbit.vborovik.competence.userservice.types.v1.CreateBillRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.DefaultResponse;

/**
 * Сервис сущности счета на оплату {@link ru.axbit.domain.domain.transaction.Bill}.
 * Описание основных методов.
 */
public interface BillService {
    DefaultResponse createBill(CreateBillRequest body);
}
