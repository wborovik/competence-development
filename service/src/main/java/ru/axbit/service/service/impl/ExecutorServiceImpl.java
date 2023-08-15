package ru.axbit.service.service.impl;

import org.springframework.stereotype.Service;
import ru.axbit.service.service.ExecutorService;
import ru.axbit.vborovik.competence.userservice.types.GetExecutorListRequest;
import ru.axbit.vborovik.competence.userservice.types.GetExecutorListResponse;

@Service
public class ExecutorServiceImpl implements ExecutorService {
    @Override
    public GetExecutorListResponse getExecutorList(GetExecutorListRequest body) {
        var response = new GetExecutorListResponse();

        return response;
    }
}
