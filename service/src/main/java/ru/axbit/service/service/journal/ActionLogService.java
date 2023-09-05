package ru.axbit.service.service.journal;

import org.apache.cxf.message.Message;
import ru.axbit.domain.domain.journal.ActionLog;

/**
 * Интерфейс сервиса класса логирования.
 */
public interface ActionLogService {
    void attachContextToRecord(ActionLog record, Message message);

    ActionLog createRecord(Message requestMessage);

    void updateCompleteRecord(ActionLog record, Message requestMessage);
}
