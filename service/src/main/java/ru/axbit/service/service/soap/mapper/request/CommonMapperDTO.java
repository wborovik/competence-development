package ru.axbit.service.service.soap.mapper.request;

import ru.axbit.service.service.soap.dto.CustomerCriteriaDTO;
import ru.axbit.service.service.soap.dto.ExecutorCriteriaDTO;
import ru.axbit.service.service.soap.dto.OrderCriteriaDTO;
import ru.axbit.vborovik.competence.filtertypes.v1.GetCustomerListFilterType;
import ru.axbit.vborovik.competence.filtertypes.v1.GetExecutorListFilterType;
import ru.axbit.vborovik.competence.filtertypes.v1.GetOrderListFilterType;
import ru.axbit.vborovik.competence.filtertypes.v1.KeyValuePair;

import static java.util.Optional.ofNullable;

/**
 * Преобразование входных типов данных методов в DTO сущностей.
 */
public class CommonMapperDTO {

    /**
     * Метод, преобразующий входные данные SOAP типа {@link GetCustomerListFilterType}
     * к DTO типу заказчика {@link CustomerCriteriaDTO}.
     *
     * @param filter Принимает SOAP класс {@link GetCustomerListFilterType}, содержащий переданные данные.
     * @return Возвращает {@link CustomerCriteriaDTO} заказчика {@link ru.axbit.domain.domain.user.Customer}.
     */
    public static CustomerCriteriaDTO mapCustomerDTO(GetCustomerListFilterType filter) {
        CustomerCriteriaDTO.CustomerCriteriaDTOBuilder builder = CustomerCriteriaDTO.builder();
        return builder
                .customerId(filter.getCustomerId())
                .customerName(filter.getCustomerName())
                .customerSurname(filter.getCustomerSurname())
                .isDeleted(false)
                .build();
    }

    /**
     * Метод, преобразующий входные данные SOAP типа {@link GetExecutorListFilterType}
     * к DTO типу исполнителя {@link ExecutorCriteriaDTO}.
     *
     * @param filter Принимает SOAP класс {@link GetExecutorListFilterType}, содержащий переданные данные.
     * @return Возвращает {@link ExecutorCriteriaDTO} исполнителя {@link ru.axbit.domain.domain.user.Executor}.
     */
    public static ExecutorCriteriaDTO mapExecutorDTO(GetExecutorListFilterType filter) {
        ExecutorCriteriaDTO.ExecutorCriteriaDTOBuilder builder = ExecutorCriteriaDTO.builder();
        return builder
                .executorId(filter.getExecutorId())
                .executorName(filter.getExecutorName())
                .executorSurname(filter.getExecutorSurname())
                .isDeleted(false)
                .build();
    }

    /**
     * Метод, преобразующий входные данные SOAP типа {@link GetOrderListFilterType}
     * к DTO типу заказа {@link OrderCriteriaDTO}.
     *
     * @param filter Принимает SOAP класс {@link GetOrderListFilterType}, содержащий переданные данные.
     * @return Возвращает {@link OrderCriteriaDTO} заказа {@link ru.axbit.domain.domain.order.WorkOrder}.
     */
    public static OrderCriteriaDTO mapOrderDTO(GetOrderListFilterType filter) {
        OrderCriteriaDTO.OrderCriteriaDTOBuilder builder = OrderCriteriaDTO.builder();
        return builder
                .orderId(filter.getOrderId())
                .customerId(filter.getCustomerId())
                .executorId(filter.getExecutorId())
                .orderDataPath(ofNullable(filter.getOrderData())
                        .flatMap(orgData -> ofNullable(orgData.getKey()))
                        .map(key -> key.split("\\."))
                        .orElse(null))
                .orderDataValue(ofNullable(filter.getOrderData())
                        .map(KeyValuePair::getValue)
                        .filter(orgData -> !orgData.isBlank())
                        .orElse(null))
                .isDeleted(false)
                .build();
    }
}
