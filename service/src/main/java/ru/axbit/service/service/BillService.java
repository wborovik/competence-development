package ru.axbit.service.service;

import ru.axbit.vborovik.competence.userservice.types.v1.CreateBillRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.DefaultResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.GetBillListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetBillListResponse;

/**
 * Сервис сущности счета на оплату {@link ru.axbit.domain.domain.transaction.Bill}.
 * Описание основных методов.
 */
public interface BillService {
    DefaultResponse createBill(CreateBillRequest body);

    GetBillListResponse getBillList(GetBillListRequest body);
}
