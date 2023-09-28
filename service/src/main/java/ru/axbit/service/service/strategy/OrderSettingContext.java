package ru.axbit.service.service.strategy;

import lombok.Setter;

/**
 * Класс, который хранит ссылку на объект конкретной стратегии поиска исполнителя по заданным настройкам заказа.
 */
@Setter
public class OrderSettingContext {
    private OrderSettingStrategy strategy;
}
