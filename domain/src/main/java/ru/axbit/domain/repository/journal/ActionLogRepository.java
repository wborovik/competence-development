package ru.axbit.domain.repository.journal;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.axbit.domain.domain.journal.ActionLog;

/**
 * Репозиторий сервиса журналирования запроса/ответа в БД.
 */
public interface ActionLogRepository extends JpaRepository<ActionLog, Long> {
}
