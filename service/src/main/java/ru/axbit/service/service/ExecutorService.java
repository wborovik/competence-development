package ru.axbit.service.service;

import ru.axbit.vborovik.competence.userservice.types.GetExecutorListRequest;
import ru.axbit.vborovik.competence.userservice.types.GetExecutorListResponse;

public interface ExecutorService {
    GetExecutorListResponse getExecutorList(GetExecutorListRequest body);
}
