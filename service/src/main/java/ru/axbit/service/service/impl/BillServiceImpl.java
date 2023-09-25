package ru.axbit.service.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.axbit.service.service.BillService;
import ru.axbit.service.service.common.AbstractCommonService;

/**
 * Реализация основных CRUD методов сущности счета на оплату {@link ru.axbit.domain.domain.transaction.Bill}.
 */
@Service
@Transactional
@AllArgsConstructor
public class BillServiceImpl extends AbstractCommonService implements BillService {
}
