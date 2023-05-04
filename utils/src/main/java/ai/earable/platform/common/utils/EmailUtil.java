package ai.earable.platform.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by BinhNH on 04/06/23
 */
public final class EmailUtil {


    public static boolean isValidEmail(String email) {
        String regex = "^(.+)@(.+)$";

        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }
}
