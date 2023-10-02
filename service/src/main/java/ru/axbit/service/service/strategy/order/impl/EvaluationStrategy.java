package ru.axbit.service.service.strategy.order.impl;

import ru.axbit.domain.domain.user.Executor;
import ru.axbit.service.service.strategy.order.OrderSettingStrategy;
import ru.axbit.vborovik.competence.filtertypes.v1.OrderSettingsType;

/**
 * Реализация стратегии подбора исполнителя по категории работ и оценке исполнителя.
 */
public class EvaluationStrategy implements OrderSettingStrategy {
    @Override
    public Executor getExecutorByOrderSettings(OrderSettingsType orderSettingsType) {

        return null;
    }
}
