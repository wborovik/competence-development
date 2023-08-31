package ru.axbit.service.service.journal.impl;

import lombok.Getter;
import ru.axbit.domain.domain.journal.ActionLog;

/**
 * {@link ThreadLocal} контекст логирования действий пользователя.
 */
public class ActionLogContext {
    @Getter
    private static final ThreadLocal<ActionLog> USER_ACTION_LOG = new ThreadLocal<>();
}
