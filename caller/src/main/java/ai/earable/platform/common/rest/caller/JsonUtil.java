package ai.earable.platform.common.rest.caller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JsonUtil {
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = (new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL));
    }

    public JsonUtil(){}

    public static <T> byte[] convertObjectToBytes(T t) throws JsonProcessingException {
        return MAPPER.writeValueAsBytes(t);
    }

    public static <T> T convertJsonToObject(String json, Class<T> outputType) throws JsonProcessingException {
        return MAPPER.readValue(json, outputType);
    }
}
