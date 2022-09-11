package ai.earable.platform.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by BinhNH on 9/10/22
 */
public final class YamlUtils {
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = (new ObjectMapper(new YAMLFactory())).findAndRegisterModules();
    }

    public static <T> T readFromYamlFile(String yamlFilePath, Class<T> outputType) throws IOException {
        return MAPPER.readValue(new File(yamlFilePath), outputType);
    }

    public static <T> T readFromBytes(byte[] bytes, Class<T> outputType) throws IOException {
        return MAPPER.readValue(bytes, outputType);
    }
}
