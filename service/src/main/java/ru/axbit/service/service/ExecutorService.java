package ru.axbit.service.service;

import ru.axbit.vborovik.competence.userservice.types.v1.CreateExecutorRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.DefaultResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.EditExecutorRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetExecutorListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetExecutorListResponse;

/**
 * Сервис сущности исполнителя {@link ru.axbit.domain.domain.user.Executor}.
 * Описание основных методов.
 */
public interface ExecutorService {
    GetExecutorListResponse getExecutorList(GetExecutorListRequest body);

    DefaultResponse editExecutor(EditExecutorRequest body);

    DefaultResponse createExecutor(CreateExecutorRequest body);
}
