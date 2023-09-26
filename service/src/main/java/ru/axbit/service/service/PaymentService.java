package ru.axbit.service.service;

import ru.axbit.vborovik.competence.userservice.types.v1.DefaultResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.MakeOrderPaymentRequest;

/**
 * Сервис сущности оплаты счета {@link ru.axbit.domain.domain.transaction.Payment}.
 * Описание основных методов.
 */
public interface PaymentService {
    DefaultResponse makeOrderPayment(MakeOrderPaymentRequest body);
}
