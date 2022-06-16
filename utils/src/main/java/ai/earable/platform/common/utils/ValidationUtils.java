package ai.earable.platform.common.utils;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {
    public static void checkBlank(String attrName, String attrValue) {
        if (StringUtils.isBlank(attrValue))
            throw new EarableException(400, EarableErrorCode.PARAM_REQUIRED, attrName);
    }

    public static <T> void checkBlankValueMap(String attrName, Map<T, String> map) {
        if (map == null) {
            throw new EarableException(400, EarableErrorCode.PARAM_REQUIRED, attrName);
        } else if (map.size() > 0) {
            map.forEach((key, value) -> {
                if (StringUtils.isBlank(value)) {
                    throw new EarableException(400, EarableErrorCode.PARAM_INVALID, attrName);
                }
            });
        }
    }

    public static void checkNull(String attrName, Object value) {
        if (value == null) {
            throw new EarableException(400, EarableErrorCode.PARAM_REQUIRED, attrName);
        }
    }

    public static void isValidURL(String attrName, String attrValue) {
        if (!isValidURL(attrValue)) {
            throw new EarableException(400, EarableErrorCode.PARAM_REQUIRED, attrName);
        }
    }

    public static void isValidUUID(String id) {
        String regex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(id);
        if (!m.matches()) {
            throw new EarableException(400, EarableErrorCode.UUID_INVALID, id);
        }

    }

    private static boolean isValidURL(String url) {
        if (StringUtils.isEmpty(url)) {
            return true;
        }

        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
