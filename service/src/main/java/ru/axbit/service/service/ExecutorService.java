package ru.axbit.service.service;

import ru.axbit.vborovik.competence.userservice.types.v1.GetExecutorListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetExecutorListResponse;

public interface ExecutorService {
    GetExecutorListResponse getExecutorList(GetExecutorListRequest body);
}
