package ru.axbit.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.axbit.domain.domain.common.Change;

/**
 * Репозиторий сервиса журналирования запроса/ответа в БД.
 */
public interface ChangeRepository extends JpaRepository<Change, Long> {
}
