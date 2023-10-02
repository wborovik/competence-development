package ru.axbit.service.service.strategy.order.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axbit.domain.domain.user.Executor;
import ru.axbit.domain.repository.ExecutorRepository;
import ru.axbit.service.service.strategy.order.OrderSettingStrategy;
import ru.axbit.vborovik.competence.filtertypes.v1.OrderSettingsType;

/**
 * Реализация стратегии подбора исполнителя только по категории работ.
 */
@Service
@AllArgsConstructor
public class CategoryStrategy implements OrderSettingStrategy {
    private final ExecutorRepository executorRepository;

    @Override
    public Executor getExecutorByOrderSettings(OrderSettingsType orderSettingsType) {
        var workCategory = orderSettingsType.getWorkCategory();
        return null;
    }
}
