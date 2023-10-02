package ru.axbit.service.service.strategy.order;

import lombok.Setter;
import ru.axbit.domain.domain.user.Executor;
import ru.axbit.vborovik.competence.filtertypes.v1.OrderSettingsType;

/**
 * Класс, который хранит ссылку на объект конкретной стратегии поиска исполнителя по заданным настройкам заказа.
 */
@Setter
public class OrderSettingContext {
    private OrderSettingStrategy strategy;

    public Executor getExecutorByOrderService(OrderSettingsType orderSettingsType) {
        return null;
    }
}
