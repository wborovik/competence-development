package ru.axbit.service.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.axbit.domain.domain.common.AbstractEntity;
import ru.axbit.domain.domain.common.AuditEntity;
import ru.axbit.domain.domain.user.Executor;
import ru.axbit.domain.repository.ExecutorRepository;
import ru.axbit.service.exception.BusinessExceptionEnum;
import ru.axbit.service.service.ExecutorService;
import ru.axbit.service.service.soap.mapper.request.CommonMapperDTO;
import ru.axbit.service.service.soap.mapper.response.ExecutorListPojo;
import ru.axbit.service.service.soap.mapper.response.ResponseMapper;
import ru.axbit.service.service.soap.spec.ExecutorSpecification;
import ru.axbit.service.util.PagingUtils;
import ru.axbit.vborovik.competence.core.v1.PagingOptions;
import ru.axbit.vborovik.competence.filtertypes.v1.GetExecutorListFilterType;
import ru.axbit.vborovik.competence.userservice.types.v1.CreateExecutorRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.DefaultResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.DeleteExecutorRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.EditExecutorRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetExecutorListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetExecutorListResponse;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

/**
 * Реализация основных CRUD методов сущности исполнителя {@link Executor}.
 */
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

    /**
     * Метод для получения списка исполнителей {@link Executor}.
     *
     * @param filter        принимает тип {@link GetExecutorListFilterType}, содержащий критерии поиска.
     * @param pagingOptions принимает тип {@link PagingOptions}, содержащий условия сортировки страниц.
     * @return Возвращает {@link ExecutorListPojo}, который содержит страницы исполнителей, полученных из БД.
     */
    private ExecutorListPojo getExecutorList(GetExecutorListFilterType filter, PagingOptions pagingOptions) {
        if (Objects.isNull(filter)) {
            return null;
        }
        var pageRequest = PagingUtils.getPageRequest(pagingOptions);
        var sorting = PagingUtils.getSortOptions(pagingOptions, AbstractEntity.Fields.id);
        var criteriaDto = CommonMapperDTO.mapExecutorDTO(filter);
        Specification<Executor> specification = ExecutorSpecification.create(criteriaDto, sorting);
        Page<Executor> executors = executorRepository.findAll(specification, pageRequest);

        return ExecutorListPojo.builder()
                .executors(executors)
                .build();
    }

    /**
     * Метод для изменения исполнителя {@link Executor}.
     *
     * @param body передается SOAP тип {@link EditExecutorRequest} с вносимыми изменениями.
     * @return возвращается SOAP тип {@link DefaultResponse}, содержащий статус проведенной операции.
     */
    @Override
    public DefaultResponse editExecutor(EditExecutorRequest body) {
        var editExecutorReq = body.getEditExecutor();
        var executorId = editExecutorReq.getId();
        var executorOptional = executorRepository.findById(executorId);
        executorOptional.filter(AuditEntity::isDeleted)
                .ifPresent(executor -> BusinessExceptionEnum.E002
                        .thr(executor.getId(), Executor.class.getSimpleName()));
        if (executorOptional.isPresent()) {
            var executor = executorOptional.get();
            Optional.ofNullable(editExecutorReq.getExecutorName()).ifPresent(executor::setName);
            Optional.ofNullable(editExecutorReq.getExecutorSurname()).ifPresent(executor::setSurname);
            Optional.ofNullable(editExecutorReq.getExecutorAge()).ifPresent(executor::setAge);
            executorRepository.save(executor);
        } else {
            BusinessExceptionEnum.E001.thr(executorId, Executor.class.getSimpleName());
        }
        return ResponseMapper.mapDefaultResponse(true);
    }

    /**
     * Метод для создания исполнителя {@link Executor}.
     *
     * @param body принимает SOAP тип {@link CreateExecutorRequest} с сохраняемыми данными.
     * @return возвращает SOAP тип {@link DefaultResponse}, содержащий статус проведенной операции.
     */
    @Override
    public DefaultResponse createExecutor(CreateExecutorRequest body) {
        var createExecutorReq = body.getCreateExecutor();
        var executor = new Executor();
        Optional.ofNullable(createExecutorReq.getExecutorName()).ifPresent(executor::setName);
        Optional.ofNullable(createExecutorReq.getExecutorSurname()).ifPresent(executor::setSurname);
        Optional.ofNullable(createExecutorReq.getExecutorAge()).ifPresent(executor::setAge);
        executorRepository.save(executor);

        return ResponseMapper.mapDefaultResponse(true);
    }

    /**
     * Метод для удаления клиента {@link Executor}.
     *
     * @param body принимает SOAP тип {@link DeleteExecutorRequest}, в котором указан идентификатор записи.
     * @return возвращает SOAP тип {@link DefaultResponse}, содержащий статус проведенной операции.
     */
    @Override
    public DefaultResponse deleteExecutor(DeleteExecutorRequest body) {
        var deleteExecutorReq = body.getDeleteExecutor();
        var executorId = deleteExecutorReq.getId();
        var executorOptional = executorRepository.findById(executorId);
        executorOptional.filter(AuditEntity::isDeleted)
                .ifPresent(executor -> BusinessExceptionEnum.E002
                        .thr(executor.getId(), Executor.class.getSimpleName()));
        CustomerServiceImpl.deleteEntity(executorOptional, executorId, Executor.class.getSimpleName());

        return ResponseMapper.mapDefaultResponse(true);
    }
}
