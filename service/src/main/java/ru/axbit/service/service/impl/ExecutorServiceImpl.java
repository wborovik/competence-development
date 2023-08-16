package ru.axbit.service.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axbit.domain.domain.user.Executor;
import ru.axbit.domain.repository.ExecutorRepository;
import ru.axbit.service.service.ExecutorService;
import ru.axbit.service.service.soap.mapper.response.ResponseMapper;
import ru.axbit.vborovik.competence.filtertypes.GetExecutorListFilterType;
import ru.axbit.vborovik.competence.userservice.types.GetExecutorListRequest;
import ru.axbit.vborovik.competence.userservice.types.GetExecutorListResponse;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class ExecutorServiceImpl implements ExecutorService {
    private final ExecutorRepository executorRepository;

    @Override
    public GetExecutorListResponse getExecutorList(GetExecutorListRequest body) {
        var executors = getExecutor(body.getFilter());

        return ResponseMapper.mapGetExecutorResponse(executors);
    }

    private Set<Executor> getExecutor(GetExecutorListFilterType filter) {
        if (Objects.isNull(filter)) return new HashSet<>();
        Set<Long> executorIds = new HashSet<>(filter.getExecutorId());

        return executorRepository.findAllByIdInAndDeletedIsFalse(executorIds);
    }
}
