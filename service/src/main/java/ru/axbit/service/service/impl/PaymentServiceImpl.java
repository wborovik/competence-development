package ru.axbit.service.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.axbit.domain.domain.transaction.Bill;
import ru.axbit.domain.domain.transaction.Payment;
import ru.axbit.domain.repository.PaymentRepository;
import ru.axbit.domain.repository.WorkOrderRepository;
import ru.axbit.service.exception.BusinessException;
import ru.axbit.service.exception.BusinessExceptionEnum;
import ru.axbit.service.service.PaymentService;
import ru.axbit.service.service.common.AbstractCommonService;
import ru.axbit.service.service.soap.mapper.response.ResponseMapper;
import ru.axbit.service.util.TableNameConst;
import ru.axbit.vborovik.competence.userservice.types.v1.DefaultResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.MakeOrderPaymentRequest;

import java.util.Objects;

/**
 * Реализация основных CRUD методов сущности оплаты счета {@link ru.axbit.domain.domain.transaction.Payment}.
 */
@Service
@Transactional
@AllArgsConstructor
public class PaymentServiceImpl extends AbstractCommonService implements PaymentService {

    private final WorkOrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    /**
     * Метод для совершения оплаты заказа.
     *
     * @param body передается SOAP тип {@link MakeOrderPaymentRequest} с данными заказа.
     * @return возвращает SOAP тип {@link DefaultResponse}, содержащий статус проведенной операции.
     */
    @Override
    public DefaultResponse makeOrderPayment(MakeOrderPaymentRequest body) {
        var filter = body.getMakeOrderPayment();
        var orderId = filter.getOrderId();
        var order = findEntityById(orderId, orderRepository, TableNameConst.ORDER_TABLE_NAME);
        var bill = order.getBill();
        checkIfExistsBill(bill, orderId);
        var payment = new Payment();
        payment.setWorkOrder(order);
        paymentRepository.save(payment);
        bill.setPayment(true);

        return ResponseMapper.mapDefaultResponse(true);
    }

    /**
     * Вспомогательный метод, который проверяет наличие активного счета на оплату.
     *
     * @param bill    передается счет {@link Bill}.
     * @param orderId передается идентификатор заказа {@link ru.axbit.domain.domain.order.WorkOrder}.
     */
    private void checkIfExistsBill(Bill bill, Long orderId) {
        if (Objects.isNull(bill) || bill.isDeleted() || bill.isPayment()) {
            throw new BusinessException(BusinessExceptionEnum.E007, orderId);
        }
    }
}
