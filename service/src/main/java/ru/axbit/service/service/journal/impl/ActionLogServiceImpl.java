package ru.axbit.service.service.journal.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.cxf.binding.soap.SoapBindingConstants;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.DelegatingInputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.staxutils.PrettyPrintXMLStreamWriter;
import org.apache.cxf.staxutils.StaxUtils;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import ru.axbit.domain.domain.journal.ActionLog;
import ru.axbit.domain.repository.journal.ActionLogRepository;
import ru.axbit.service.service.journal.ActionLogService;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.io.StringWriter;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ActionLogServiceImpl implements ActionLogService {
    private final ActionLogRepository actionLogRepository;

    @Override
    public ActionLog createRecord(Message requestMessage) {
        var log = new ActionLog();
        log.setRequest(getStringMessage(requestMessage));

        return log;
    }

    @Override
    public void attachContextToRecord(ActionLog log, Message requestMessage) {
        log.setMethodName(getMethodName(requestMessage));

    }

    private String getMethodName(Message message) {
        var method = message.get(SoapBindingConstants.SOAP_ACTION);
        Preconditions.checkNotNull(method, "Method not found");
        var path = message.get(Message.PATH_INFO);
        Preconditions.checkNotNull(path, "Path not found");

        return method.toString();
    }

    @Override
    public void updateCompleteRecord(ActionLog record, Message responseMessage) {
        if (Objects.nonNull(record)) {
            record.setResponse(getStringMessage(responseMessage));
            actionLogRepository.save(record);
        }
    }

    @SneakyThrows
    private String getStringMessage(Message message) {
        var buffer = new StringBuilder();

        var encoding = (String) message.get(Message.ENCODING);
        if (Objects.nonNull(encoding)) {
            buffer.append("Encoding: ").append(encoding).append("\n");
        }

        var contentType = (String) message.get("Content-Type");
        if (Objects.nonNull(contentType)) {
            buffer.append("ContentType: ").append(contentType).append("\n");
        }

        var url = (String) message.get("org.apache.cxf.request.url");
        if (Objects.nonNull(url)) {
            buffer.append("url: ").append(url).append("\n");
        }

        var requestStream = message.getContent(InputStream.class);
        if (Objects.nonNull(requestStream)) {
            buffer.append(getContentFromInputStream(message, requestStream, encoding, contentType));
        }

        var responseStream = message.getContent(OutputStream.class);
        if (Objects.nonNull(responseStream)) {
            var response = new StringBuilder();
            writePayload(response, CastUtils.cast(responseStream), encoding, contentType);
            buffer.append(response);
        }
        return buffer.toString();
    }

    protected String getContentFromInputStream(
            Message message, InputStream requestStream, String encoding, String contentType) {
        var cachedOutputStream = new CachedOutputStream();
        try {
            var result = new StringBuilder();
            var inputStream = requestStream instanceof DelegatingInputStream
                    ?
                    ((DelegatingInputStream) requestStream).getInputStream()
                    :
                    requestStream;
            IOUtils.copyAtLeast(inputStream, cachedOutputStream, 2147483647);
            cachedOutputStream.flush();
            var sequenceInputStream = new SequenceInputStream(cachedOutputStream.getInputStream(), inputStream);
            if (requestStream instanceof DelegatingInputStream) {
                ((DelegatingInputStream) requestStream).setInputStream(sequenceInputStream);
            } else {
                message.setContent(InputStream.class, sequenceInputStream);
            }
            writePayload(result, cachedOutputStream, encoding, contentType);
            cachedOutputStream.close();
            return result.toString();
        } catch (Exception e) {
            throw new Fault(e);
        }
    }

    private void writePayload(
            StringBuilder builder, CachedOutputStream cos, String encoding, String contentType) throws Exception {
        if (Objects.nonNull(contentType) && contentType.contains("xml") && cos.size() > 0L) {
            try (var stringWriter = new StringWriter()) {
                var xmlStreamWriter = StaxUtils.createXMLStreamWriter(stringWriter);
                var prettyPrintXMLStreamWriter = new PrettyPrintXMLStreamWriter(xmlStreamWriter, 2);

                try (InputStream in = cos.getInputStream()) {
                    var inputStreamReader = StringUtils.isEmpty(encoding)
                            ? new InputStreamReader(in) : new InputStreamReader(in, encoding);
                    StaxUtils.copy(new StreamSource(inputStreamReader), prettyPrintXMLStreamWriter);
                } catch (XMLStreamException ignored) {
                } finally {
                    try {
                        prettyPrintXMLStreamWriter.flush();
                        prettyPrintXMLStreamWriter.close();
                    } catch (XMLStreamException ignored) {
                    }
                }
                var result = stringWriter.toString();
                builder.append(result);
            } catch (Throwable e) {
                e.getSuppressed();
            }
        } else if (StringUtils.isEmpty(encoding)) {
            cos.writeCacheTo(builder, -1);
        } else {
            cos.writeCacheTo(builder, encoding, -1);
        }
    }
}
