package ru.axbit.service.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.axbit.domain.domain.common.AbstractEntity;
import ru.axbit.domain.domain.user.Executor;
import ru.axbit.domain.repository.ExecutorRepository;
import ru.axbit.service.service.ExecutorService;
import ru.axbit.service.service.soap.mapper.request.CommonMapperDTO;
import ru.axbit.service.service.soap.mapper.response.ExecutorListPojo;
import ru.axbit.service.service.soap.mapper.response.ResponseMapper;
import ru.axbit.service.service.soap.spec.ExecutorSpecification;
import ru.axbit.service.util.PagingUtils;
import ru.axbit.vborovik.competence.core.v1.PagingOptions;
import ru.axbit.vborovik.competence.filtertypes.v1.GetExecutorListFilterType;
import ru.axbit.vborovik.competence.userservice.types.v1.DefaultResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.EditExecutorRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetExecutorListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetExecutorListResponse;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ExecutorServiceImpl implements ExecutorService {
    private final ExecutorRepository executorRepository;

    @Override
    public GetExecutorListResponse getExecutorList(GetExecutorListRequest body) {
        var executorPojo = getExecutorList(body.getFilter(), body.getPagingOptions());

        return ResponseMapper.mapGetExecutorResponse(executorPojo);
    }

    private ExecutorListPojo getExecutorList(GetExecutorListFilterType filter, PagingOptions pagingOptions) {
        if (Objects.isNull(filter)) return null;
        var pageRequest = PagingUtils.getPageRequest(pagingOptions);
        var sorting = PagingUtils.getSortOptions(pagingOptions, AbstractEntity.Fields.id);
        var criteriaDto = CommonMapperDTO.mapExecutorDTO(filter);
        Specification<Executor> specification = ExecutorSpecification.create(criteriaDto, sorting);
        Page<Executor> executors = executorRepository.findAll(specification, pageRequest);

        return ExecutorListPojo.builder()
                .executors(executors)
                .build();
    }

    @Override
    public DefaultResponse editExecutor(EditExecutorRequest body) {
        var editExecutorReq = body.getEditExecutor();
        var executorId = editExecutorReq.getId();
        var executorOptional = executorRepository.findById(executorId);
        if (executorOptional.isPresent()) {
            var executor = executorOptional.get();
            Optional.ofNullable(editExecutorReq.getExecutorName()).ifPresent(executor::setName);
            Optional.ofNullable(editExecutorReq.getExecutorSurname()).ifPresent(executor::setSurname);
            Optional.ofNullable(editExecutorReq.getExecutorAge()).ifPresent(executor::setAge);
            executor.setChanged(LocalDateTime.now());
            executorRepository.save(executor);
        }
        return ResponseMapper.mapDefaultResponse(true);
    }
}
