package ru.axbit.service.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.axbit.service.service.PaymentService;
import ru.axbit.service.service.common.AbstractCommonService;

/**
 * Реализация основных CRUD методов сущности оплаты счета {@link ru.axbit.domain.domain.transaction.Payment}.
 */
@Service
@Transactional
@AllArgsConstructor
public class PaymentServiceImpl extends AbstractCommonService implements PaymentService {
}
