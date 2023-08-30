package ru.axbit.service.service.json;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Интерфейс, предоставляющий методы маппинга json структур.
 */
public interface JsonMappingService {
    JsonNode mapToJsonNode(String json);

    <T> JsonNode mapToJsonNode(T object);
}
