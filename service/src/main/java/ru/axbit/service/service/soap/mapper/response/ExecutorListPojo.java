package ru.axbit.service.service.soap.mapper.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import ru.axbit.domain.domain.user.Executor;

/**
 * Класс, содержащий {@link Page} исполнителей {@link Executor}.
 * Используется методами, которые делают выборку из БД партиями.
 */
@Getter
@Builder
public class ExecutorListPojo {
    private final Page<Executor> executors;
}
