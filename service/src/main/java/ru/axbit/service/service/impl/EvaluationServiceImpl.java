package ru.axbit.service.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.axbit.domain.domain.evaluation.Evaluation;
import ru.axbit.domain.domain.user.Executor;
import ru.axbit.domain.repository.EvaluationRepository;
import ru.axbit.domain.repository.ExecutorRepository;
import ru.axbit.service.service.EvaluationService;
import ru.axbit.service.service.soap.mapper.response.ResponseMapper;
import ru.axbit.vborovik.competence.userservice.types.v1.DefaultResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.EditEvaluationRequest;

/**
 * Реализация основных CRUD методов сущности оценки исполнителя.
 */
@Service
@Transactional
@AllArgsConstructor
public class EvaluationServiceImpl implements EvaluationService {
    private final ExecutorRepository executorRepository;
    private final EvaluationRepository evaluationRepository;

    /**
     * Метод для изменения оценки {@link Evaluation}.
     *
     * @param body передается SOAP тип {@link EditEvaluationRequest} с вносимыми изменениями.
     *             Указывается идентификатор исполнителя {@link Executor}, у которого изменяется оценка.
     * @return возвращается SOAP тип {@link DefaultResponse}, содержащий статус проведенной операции.
     */
    @Override
    public DefaultResponse editEvaluation(EditEvaluationRequest body) {
        var editEvaluationReq = body.getEditEvaluation();
        var inputEvaluation = editEvaluationReq.getEvaluation();
        var executorId = editEvaluationReq.getId();
        var executor = executorRepository.findById(executorId);

        var evaluation = executor.map(Executor::getEvaluation);
        evaluation.ifPresent(e -> {
            var count = e.getCount() + 1;
            var bdEvaluation = e.getEvaluation();
            var newEvaluation = (bdEvaluation * (count - 1) + inputEvaluation) / count;
            e.setEvaluation(newEvaluation);
            e.setCount(count);
            executor.ifPresent(executorRepository::save);
        });
        return ResponseMapper.mapDefaultResponse(true);
    }

    /**
     * Метод создает новую оценку {@link Evaluation}.
     *
     * @return возвращается, созданная оценка.
     */
    @Override
    public Evaluation createEvaluation() {
        var newEvaluation = new Evaluation();
        newEvaluation.setCount(0);
        newEvaluation.setEvaluation(0);
        evaluationRepository.save(newEvaluation);
        return newEvaluation;
    }
}
