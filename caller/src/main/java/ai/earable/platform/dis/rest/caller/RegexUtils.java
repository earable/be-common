package ai.earable.platform.dis.rest.caller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexUtils {
    public static boolean isValidTimestamp(String timestamp){
        Pattern pattern = Pattern.compile(".*\\/[0-9]{10}\\..*"); //.*\/[0-9]{10}\..*
        Matcher matcher = pattern.matcher(timestamp);
        return matcher.matches();
    }

    public static void main(String[] args) {
        System.out.println(isValidTimestamp(String.valueOf(1647538275.31)));
    }
}
