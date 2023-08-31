package ru.axbit.service.service.journal.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.stereotype.Component;
import ru.axbit.service.service.journal.ActionLogService;

import java.util.Objects;

@Slf4j
@Component
public class LogOutputInterceptor extends AbstractPhaseInterceptor<Message> {
    private final ActionLogService actionLogService;

    public LogOutputInterceptor(ActionLogService actionLogService) {
        super(Phase.POST_STREAM);
        this.actionLogService = actionLogService;
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        var record = ActionLogContext.getUSER_ACTION_LOG().get();
        if (Objects.nonNull(record)) {
            actionLogService.updateCompleteRecord(record, message);
        }
    }
}
