package ru.axbit.service.util;

import ru.axbit.domain.domain.cls.ClsOrderCategory;
import ru.axbit.domain.domain.order.WorkOrder;
import ru.axbit.domain.domain.user.Customer;
import ru.axbit.domain.domain.user.Executor;

/**
 * Вспомогательный класс, содержащий константы названия таблиц.
 */
public class TableNameConst {
    public static final String ORDER_TABLE_NAME = WorkOrder.class.getSimpleName();
    public static final String CUSTOMER_TABLE_NAME = Customer.class.getSimpleName();
    public static final String EXECUTOR_TABLE_NAME = Executor.class.getSimpleName();
    public static final String CATEGORY_TABLE_NAME = ClsOrderCategory.class.getSimpleName();
}
