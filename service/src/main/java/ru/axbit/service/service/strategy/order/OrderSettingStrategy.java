package ru.axbit.service.service.strategy.order;

import ru.axbit.domain.domain.user.Executor;
import ru.axbit.vborovik.competence.filtertypes.v1.OrderSettingsType;

/**
 * Интерфейс, описывающий стратегию поиска исполнителя по заданным настройкам заказа.
 */
public interface OrderSettingStrategy {
    Executor getExecutorByOrderSettings(OrderSettingsType orderSettingsType);
}
