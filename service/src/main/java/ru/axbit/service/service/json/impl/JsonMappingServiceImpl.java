package ru.axbit.service.service.json.impl;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.axbit.service.service.json.JsonMappingService;

/**
 * Класс маппинга json данных.
 * Реализует интерфейс {@link JsonMappingService}
 */
@Slf4j
@Service
public class JsonMappingServiceImpl implements JsonMappingService {
    @Override
    public JsonNode mapToJsonNode(String json) {
        return null;
    }
}
