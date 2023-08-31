package ru.axbit.service.service.journal.impl;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.stereotype.Component;
import ru.axbit.service.service.journal.ActionLogService;

import java.util.Objects;

@Component
public class LogInputInterceptor extends AbstractPhaseInterceptor<Message> {

    private final ActionLogService actionLogService;

    public LogInputInterceptor(ActionLogService actionLogService) {
        super(Phase.PRE_UNMARSHAL);
        this.actionLogService = actionLogService;
    }
    @Override
    public void handleMessage(Message message) throws Fault {
        var record = ActionLogContext.getUSER_ACTION_LOG().get();
        if (Objects.nonNull(record)) {
            actionLogService.attachContextToRecord(record, message);
        }
    }
}
