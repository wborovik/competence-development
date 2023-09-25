package ru.axbit.service.service.soap.mapper.response;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axbit.domain.domain.common.UserData;
import ru.axbit.domain.domain.order.WorkOrder;
import ru.axbit.domain.domain.user.Customer;
import ru.axbit.domain.domain.user.Executor;
import ru.axbit.service.util.ValidationUtils;
import ru.axbit.vborovik.competence.core.v1.CustomerPageItemType;
import ru.axbit.vborovik.competence.core.v1.CustomerPageType;
import ru.axbit.vborovik.competence.core.v1.ExecutorPageItemType;
import ru.axbit.vborovik.competence.core.v1.ExecutorPageType;
import ru.axbit.vborovik.competence.core.v1.OrderPageItemType;
import ru.axbit.vborovik.competence.core.v1.OrderPageType;
import ru.axbit.vborovik.competence.core.v1.Result;
import ru.axbit.vborovik.competence.core.v1.UserDataPageType;
import ru.axbit.vborovik.competence.userservice.types.v1.DefaultResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.GetCustomerListResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.GetExecutorListResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.GetOrderListResponse;

import java.util.Objects;
import java.util.Optional;

/**
 * Класс, содержащий статические методы мапинга для получения возвращаемого(response) SOAP значения CRUD методов.
 */
@Service
@AllArgsConstructor
public class ResponseMapper {
    /**
     * Метод, преобразующий страницы {@link org.springframework.data.domain.Page}
     * заказчика {@link Customer} в выходной SOAP тип.
     *
     * @param customerPojo принимает класс, содержащий страницы Page заказчика Customer {@link CustomerListPojo}.
     * @return Возвращает {@link GetCustomerListResponse} выходной SOAP тип.
     */
    public static GetCustomerListResponse mapGetCustomerResponse(CustomerListPojo customerPojo) {
        var response = new GetCustomerListResponse();
        if (Objects.isNull(customerPojo)) {
            return response;
        }
        var customerPageType = CommonMapper.mapPagingResults(CustomerPageType.class, customerPojo.getCustomers());
        response.setResult(customerPageType);
        var customers = customerPojo.getCustomers();
        ValidationUtils.checkIsEmptyPage(customers, Customer.class.getSimpleName());
        var pageType = response.getResult();
        var resultList = pageType.getCustomerItem();
        customers.forEach(customer -> {
            var result = mapCustomerPageItemType(customer);
            resultList.add(result);
        });
        return response;
    }

    /**
     * Метод преобразующий заказчика {@link Customer} к SOAP типу {@link CustomerPageItemType},
     * содержащему его идентификатор и пользовательские данные.
     *
     * @param customer Заказчик {@link Customer}.
     * @return {@link CustomerPageItemType} тип, содержащий id и пользовательские данные заказчика.
     */
    public static CustomerPageItemType mapCustomerPageItemType(Customer customer) {
        var result = new CustomerPageItemType();
        if (Objects.isNull(customer)) {
            return result;
        }
        result.setCustomerId(customer.getId());
        var userData = mapUserDataType(customer);
        result.setUserData(userData);
        return result;
    }

    /**
     * Метод, преобразующий страницы {@link org.springframework.data.domain.Page}
     * исполнителя {@link Executor} в выходной SOAP тип.
     *
     * @param executorPojo принимает класс, содержащий страницы Page исполнителя Executor {@link ExecutorListPojo}.
     * @return Возвращает {@link GetExecutorListResponse} выходной SOAP тип.
     */
    public static GetExecutorListResponse mapGetExecutorResponse(ExecutorListPojo executorPojo) {
        var response = new GetExecutorListResponse();
        if (Objects.isNull(executorPojo)) {
            return response;
        }
        var executorPageType = CommonMapper.mapPagingResults(ExecutorPageType.class, executorPojo.getExecutors());
        response.setResult(executorPageType);
        var executors = executorPojo.getExecutors();
        ValidationUtils.checkIsEmptyPage(executors, Executor.class.getSimpleName());
        var pageType = response.getResult();
        var resultList = pageType.getCustomerItem();
        executors.forEach(executor -> {
            var result = mapExecutorPageItemType(executor);
            resultList.add(result);
        });
        return response;
    }

    /**
     * Метод преобразующий исполнителя {@link Executor} к SOAP типу {@link ExecutorPageItemType},
     * содержащему его идентификатор и пользовательские данные.
     *
     * @param executor Исполнитель {@link Executor}.
     * @return Возвращает {@link ExecutorPageItemType} тип, содержащий id и пользовательские данные исполнителя.
     */
    public static ExecutorPageItemType mapExecutorPageItemType(Executor executor) {
        var result = new ExecutorPageItemType();
        if (Objects.isNull(executor)) {
            return result;
        }
        result.setExecutorId(executor.getId());
        var userData = mapUserDataType(executor);
        result.setUserData(userData);
        Optional.ofNullable(executor.getEvaluation()).ifPresent(e -> {
            var evaluation = e.getEvaluation();
            var scale = Math.pow(10, 1);
            evaluation = Math.ceil(evaluation * scale) / scale;
            result.setEvaluation(evaluation);
        });
        return result;
    }

    /**
     * Метод, преобразующий данные пользователя {@link UserData} к SOAP типу {@link UserDataPageType},
     * который также хранит эти данные.
     *
     * @param userData Данные пользователя {@link UserData}.
     * @return {@link UserDataPageType} SOAP тип, хранящий данные пользователя.
     */
    public static UserDataPageType mapUserDataType(UserData userData) {
        var userDataType = new UserDataPageType();
        userDataType.setName(userData.getName());
        userDataType.setSurname(userData.getSurname());
        userDataType.setAge(userData.getAge());
        userDataType = (Objects.isNull(userDataType.getName())
                && Objects.isNull(userDataType.getSurname()) && Objects.isNull(userDataType.getAge()))
                ?
                null : userDataType;
        return userDataType;
    }

    /**
     * Метод, преобразующий страницы {@link org.springframework.data.domain.Page}
     * заказа {@link WorkOrder} в выходной SOAP тип.
     *
     * @param orderPojo принимает класс, содержащий страницы Page заказа WorkOrder {@link OrderListPojo}.
     * @return {@link GetOrderListResponse} выходной SOAP тип.
     */
    public static GetOrderListResponse mapGetOrderResponse(OrderListPojo orderPojo) {
        var response = new GetOrderListResponse();
        if (Objects.isNull(orderPojo)) {
            return response;
        }
        var orderPageType = CommonMapper.mapPagingResults(OrderPageType.class, orderPojo.getOrders());
        response.setResult(orderPageType);
        var orders = orderPojo.getOrders();
        ValidationUtils.checkIsEmptyPage(orders, WorkOrder.class.getSimpleName());
        var pageType = response.getResult();
        var resultList = pageType.getOrderItem();
        orders.forEach(order -> {
            var result = mapOrderPageItemType(order);
            resultList.add(result);
        });
        return response;
    }

    /**
     * Метод преобразующий заказ {@link WorkOrder} к SOAP типу {@link OrderPageItemType},
     * содержащему его идентификатор и описание.
     *
     * @param workOrder Заказ {@link WorkOrder}
     * @return Возвращает {@link OrderPageItemType} тип, содержащий id и описание заказа.
     */
    public static OrderPageItemType mapOrderPageItemType(WorkOrder workOrder) {
        var result = new OrderPageItemType();
        if (Objects.isNull(workOrder)) {
            return result;
        }
        result.setOrderId(workOrder.getId());
        result.setCustomer(mapCustomerPageItemType(workOrder.getCustomer()));
        result.setExecutor(mapExecutorPageItemType(workOrder.getExecutor()));
        return result;
    }

    /**
     * Метод для передачи true или false в SOAP ответ CRUD методов, которые возвращают не значение, а статус.
     *
     * @param status Булево значение true или false.
     * @return {@link DefaultResponse} SOAP тип, возвращающий в качестве результата статус выполняемой операции.
     */
    public static DefaultResponse mapDefaultResponse(boolean status) {
        var response = new DefaultResponse();
        var result = new Result();
        result.setValue(status);
        response.setResult(result);
        return response;
    }
}
