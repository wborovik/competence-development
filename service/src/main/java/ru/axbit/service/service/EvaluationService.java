package ru.axbit.service.service;

import ru.axbit.domain.domain.evaluation.Evaluation;
import ru.axbit.vborovik.competence.userservice.types.v1.DefaultResponse;
import ru.axbit.vborovik.competence.userservice.types.v1.EditEvaluationRequest;

/**
 * Сервис сущности оценки исполнителя.
 * Описание основных методов.
 */
public interface EvaluationService {
    DefaultResponse editEvaluation(EditEvaluationRequest body);
    Evaluation createEvaluation();
}
