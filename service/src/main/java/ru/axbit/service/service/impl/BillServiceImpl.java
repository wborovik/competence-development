package ru.axbit.service.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.axbit.domain.domain.common.AbstractEntity;
import ru.axbit.domain.domain.transaction.Bill;
import ru.axbit.domain.repository.BillRepository;
import ru.axbit.domain.repository.WorkOrderRepository;
import ru.axbit.service.exception.BusinessException;
import ru.axbit.service.exception.BusinessExceptionEnum;
import ru.axbit.service.service.BillService;
import ru.axbit.service.service.common.AbstractCommonService;
import ru.axbit.service.service.soap.mapper.request.CommonMapperDTO;
import ru.axbit.service.service.soap.mapper.response.BillListPojo;
import ru.axbit.service.service.soap.mapper.response.ResponseMapper;
import ru.axbit.service.service.soap.spec.BillSpecification;
import ru.axbit.service.util.PagingUtils;
import ru.axbit.service.util.TableNameConst;
import ru.axbit.vborovik.competence.core.v1.PagingOptions;
import ru.axbit.vborovik.competence.filtertypes.v1.GetBillListFilterType;
import ru.axbit.vborovik.competence.userservice.types.v1.CreateBillRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.DefaultResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.GetBillListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetBillListResponse;

import java.util.Objects;
import java.util.Optional;

/**
 * Реализация основных CRUD методов сущности счета на оплату {@link Bill}.
 */
@Service
@Transactional
@AllArgsConstructor
public class BillServiceImpl extends AbstractCommonService implements BillService {

    private final BillRepository billRepository;
    private final WorkOrderRepository orderRepository;

    @Override
    public GetBillListResponse getBillList(GetBillListRequest body) {
        var billPojo = getBillList(body.getFilter(), body.getPagingOptions());

        return ResponseMapper.mapGetBillResponse(billPojo);
    }

    /**
     * Метод для получения списка счетов на оплату {@link Bill}.
     *
     * @param filter        принимает тип {@link GetBillListFilterType}, содержащий критерии поиска.
     * @param pagingOptions принимает тип {@link PagingOptions}, содержащий условия сортировки страниц.
     * @return возвращает {@link BillListPojo}, который содержит страницы счетов на оплату, полученных из БД.
     */
    public BillListPojo getBillList(GetBillListFilterType filter, PagingOptions pagingOptions) {
        if (Objects.isNull(filter)) {
            return null;
        }
        var pageRequest = PagingUtils.getPageRequest(pagingOptions);
        var sorting = PagingUtils.getSortOptions(pagingOptions, AbstractEntity.Fields.id);
        var criteriaDTO = CommonMapperDTO.mapBillDTO(filter);
        Specification<Bill> specification = BillSpecification.create(criteriaDTO, sorting);
        Page<Bill> bills = billRepository.findAll(specification, pageRequest);
        return BillListPojo.builder()
                .bills(bills)
                .build();
    }

    /**
     * Метод для создания счета на оплату {@link Bill}.
     * Если счет на оплату уже существует, но не активен, его статус станет активным и данные будут обновлены.
     *
     * @param body передается SOAP тип {@link CreateBillRequest}.
     * @return возвращает SOAP тип {@link DefaultResponse}, содержащий статус проведенной операции.
     */
    @Override
    public DefaultResponse createBill(CreateBillRequest body) {
        var createBillReq = body.getCreateBill();
        var billData = createBillReq.getCreateBill();
        var order = findEntityById(billData.getOrderId(), orderRepository, TableNameConst.ORDER_TABLE_NAME);
        var bill = order.getBill();
        checkIfExistsBill(bill, order.getId());
        if (Objects.isNull(bill)) {
            bill = new Bill();
        }
        bill.setDeleted(false);
        bill.setWorkOrder(order);
        Optional.ofNullable(billData.getPrice()).ifPresent(bill::setPrice);
        billRepository.save(bill);

        return ResponseMapper.mapDefaultResponse(true);
    }

    /**
     * Вспомогательный метод, который проверяет наличие счета на оплату для заказа.
     * Если счет существует и его статус активный, будет выброшено соответствующее исключение.
     *
     * @param bill    передается заказ {@link ru.axbit.domain.domain.order.WorkOrder}.
     * @param orderId передается идентификатор заказа {@link ru.axbit.domain.domain.order.WorkOrder}.
     */
    private void checkIfExistsBill(Bill bill, Long orderId) {
        if (Objects.nonNull(bill) && bill.nonDeleted()) {
            throw new BusinessException(BusinessExceptionEnum.E006, orderId);
        }
        if (bill.isPayment()) {
            throw new BusinessException(BusinessExceptionEnum.E008, bill.getId(), orderId);
        }
    }
}
