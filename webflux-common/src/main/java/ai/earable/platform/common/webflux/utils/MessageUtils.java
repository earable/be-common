package ai.earable.platform.common.webflux.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static ai.earable.platform.common.utils.TimeUtils.*;

@Component
public class MessageUtils {
    @Autowired
    private MessageSource msgSource;

    public String getMessage(String key) {
        return getMessage(key, "");
    }

    public String getMessage(String key, Object... params) {
        List<String> paramStrs = new ArrayList<>();
        if (params != null) {
            for (Object param : params) {
                String value = String.valueOf(param);
                if (param instanceof DefaultMessageSourceResolvable) {
                    value = ((DefaultMessageSourceResolvable) param).getDefaultMessage();
                }
                paramStrs.add(value);
            }
        }
        return msgSource.getMessage(key, paramStrs.toArray(), LocaleContextHolder.getLocale());
    }

    public String getMessage(FieldError field) {
        return msgSource.getMessage(field, LocaleContextHolder.getLocale());
    }
}
