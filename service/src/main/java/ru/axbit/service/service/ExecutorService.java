package ru.axbit.service.service;

import ru.axbit.vborovik.competence.myservice.types.GetExecutorListRequest;
import ru.axbit.vborovik.competence.myservice.types.GetExecutorListResponse;

public interface ExecutorService {
    GetExecutorListResponse getExecutorList(GetExecutorListRequest body);
}
