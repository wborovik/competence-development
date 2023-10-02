package ru.axbit.service.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.axbit.domain.domain.common.AbstractEntity;
import ru.axbit.domain.domain.user.Executor;
import ru.axbit.domain.repository.ClsOrderCategoryRepository;
import ru.axbit.domain.repository.ClsWorkSpeedRepository;
import ru.axbit.domain.repository.ExecutorRepository;
import ru.axbit.service.service.EvaluationService;
import ru.axbit.service.service.ExecutorService;
import ru.axbit.service.service.common.AbstractCommonService;
import ru.axbit.service.service.soap.mapper.request.CommonMapperDTO;
import ru.axbit.service.service.soap.mapper.response.ExecutorListPojo;
import ru.axbit.service.service.soap.mapper.response.ResponseMapper;
import ru.axbit.service.service.soap.spec.ExecutorSpecification;
import ru.axbit.service.util.PagingUtils;
import ru.axbit.service.util.TableNameConst;
import ru.axbit.service.util.ValidationUtils;
import ru.axbit.vborovik.competence.core.v1.PagingOptions;
import ru.axbit.vborovik.competence.filtertypes.v1.EditExecutorType;
import ru.axbit.vborovik.competence.filtertypes.v1.GetExecutorListFilterType;
import ru.axbit.vborovik.competence.userservice.types.v1.ActivateExecutorRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.CreateExecutorRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.DefaultResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.DeleteExecutorRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.EditExecutorRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetExecutorListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetExecutorListResponse;

import javax.transaction.Transactional;
import java.util.Objects;

/**
 * Реализация основных CRUD методов сущности исполнителя {@link Executor}.
 */
@Service
@Transactional
@AllArgsConstructor
public class ExecutorServiceImpl extends AbstractCommonService implements ExecutorService {

    private final ExecutorRepository executorRepository;
    private final EvaluationService evaluationService;
    private final ClsOrderCategoryRepository categoryRepository;
    private final ClsWorkSpeedRepository workSpeedRepository;

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
        var executor = findEntityById(executorId, executorRepository, TableNameConst.EXECUTOR_TABLE_NAME);
        ValidationUtils.checkIsDeleted(executor, executorId, TableNameConst.EXECUTOR_TABLE_NAME);
        editWorkCategory(executor, editExecutorReq);
        editWorkSpeed(executor, editExecutorReq);
        setUserData(executor, executorRepository, editExecutorReq.getUserData());

        return ResponseMapper.mapDefaultResponse(true);
    }

    /**
     * Метод для изменения характеристики {@link ru.axbit.domain.domain.cls.ClsOrderCategory},
     * указывающей на категорию выполняемых работ исполнителем {@link Executor}.
     *
     * @param executor         передается исполнитель {@link Executor}.
     * @param editExecutorType передается SOAP тип, который содержит добавляемые категории работ.
     */
    private void editWorkCategory(Executor executor, EditExecutorType editExecutorType) {
        var category = editExecutorType.getWorkCategory();
        var workCategory = categoryRepository.findByDesignation(category);
        workCategory.ifPresent(executor::setWorkCategory);
    }

    /**
     * Метод для изменения характеристики {@link ru.axbit.domain.domain.cls.ClsWorkSpeed},
     * указывающей на среднюю скорость выполнения работ исполнителем {@link Executor}.
     *
     * @param executor         передается исполнитель {@link Executor}.
     * @param editExecutorType передается SOAP тип, который содержит добавляемые категории работ.
     */
    private void editWorkSpeed(Executor executor, EditExecutorType editExecutorType) {
        var speed = editExecutorType.getWorkSpeed();
        var workSpeed = workSpeedRepository.findBySpeed(speed);
        workSpeed.ifPresent(executor::setWorkSpeed);
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
        var evaluation = evaluationService.createEvaluation();
        executor.setEvaluation(evaluation);
        var addCategories = createExecutorReq.getWorkCategory();
        var newCategories = categoryRepository.findByDesignation(addCategories);
        newCategories.ifPresent(executor::setWorkCategory);
        setUserData(executor, executorRepository, createExecutorReq.getUserData());

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
        var executor = findEntityById(executorId, executorRepository, TableNameConst.EXECUTOR_TABLE_NAME);
        deleteEntity(executor, executorId, TableNameConst.EXECUTOR_TABLE_NAME);

        return ResponseMapper.mapDefaultResponse(true);
    }

    /**
     * Метод для активации удаленного исполнителя {@link Executor}.
     *
     * @param body принимает SOAP тип {@link ActivateExecutorRequest}, в котором указан идентификатор записи.
     * @return возвращает SOAP тип {@link DefaultResponse}, содержащий статус проведенной операции.
     */
    @Override
    public DefaultResponse activateExecutor(ActivateExecutorRequest body) {
        var activateExecutorReq = body.getActivateExecutor();
        var executorId = activateExecutorReq.getId();
        var executor = findEntityById(executorId, executorRepository, TableNameConst.EXECUTOR_TABLE_NAME);
        activateEntity(executor, executorId, TableNameConst.EXECUTOR_TABLE_NAME);

        return ResponseMapper.mapDefaultResponse(true);
    }
}
