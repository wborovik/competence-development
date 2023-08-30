package ru.axbit.service.service.json.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.axbit.service.exception.BusinessException;
import ru.axbit.service.exception.BusinessExceptionEnum;
import ru.axbit.service.service.json.JsonMappingService;
import org.json.JSONException;

import java.util.Objects;

/**
 * Класс маппинга json данных.
 * Реализует интерфейс {@link JsonMappingService}
 */
@Slf4j
@Service
public class JsonMappingServiceImpl implements JsonMappingService {

    private final ObjectMapper objectMapper;

    public JsonMappingServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Метод преобразует строку типа {@link String} в json структуру {@link JsonNode}.
     * При этом проверяет, соответствует ли переданная строка json формату.
     *
     * @param json передается строка json типа {@link String}.
     * @return возвращается json структура типа {@link JsonNode}.
     */
    @Override
    public JsonNode mapToJsonNode(String json) {
        try {
            var jsonObject = new JSONObject(json);
            return Objects.nonNull(json) ? objectMapper.readTree(jsonObject.toString()) : null;
        } catch (JsonProcessingException | JSONException e) {
            throw new BusinessException(BusinessExceptionEnum.E004, json);
        }
    }

    @Override
    public <T> JsonNode mapToJsonNode(T object) {
        String json = object.toString();
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new BusinessException(BusinessExceptionEnum.E004, json);
        }
        return mapToJsonNode(json);
    }
}
