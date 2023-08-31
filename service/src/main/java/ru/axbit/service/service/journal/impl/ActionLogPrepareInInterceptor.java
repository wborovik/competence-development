package ru.axbit.service.service.journal.impl;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.stereotype.Component;
import ru.axbit.service.service.journal.ActionLogService;

@Component
public class ActionLogPrepareInInterceptor extends AbstractPhaseInterceptor<Message> {
    private final ActionLogService actionLogService;

    public ActionLogPrepareInInterceptor(ActionLogService actionLogService) {
        super(Phase.RECEIVE);
        this.actionLogService = actionLogService;
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        var record = actionLogService.createRecord(message);
        ActionLogContext.getUSER_ACTION_LOG().set(record);
    }
}
