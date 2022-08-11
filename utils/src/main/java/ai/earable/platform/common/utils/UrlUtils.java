package ai.earable.platform.common.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Created by BinhNH on 7/10/2022
 */
public final class UrlUtils {
    public static String encode(String input){
        return URLEncoder.encode(input, StandardCharsets.UTF_8);
    }

    public static String decode(String input){
        return URLDecoder.decode(input, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {

//        String expr = "lifetime(SLEEP_STAGES{sessionId=\"__sessionId__\"}[__dayWindow__])/3600-(tfirst_over_time((SLEEP_STAGES{sessionId=\"__sessionId__\"}==2)[__dayWindow__])@end()-tfirst_over_time((SLEEP_STAGES{sessionId=\"__sessionId__\"})[__dayWindow__])@end())/60-duration_over_time((SLEEP_STAGES{sessionId=\"__sessionId__\"}==3)[__dayWindow__],30s)@end()/60-(tfirst_over_time((SLEEP_STAGES{sessionId=\"__sessionId__\"}==2)[__dayWindow__])@end()-tfirst_over_time((SLEEP_STAGES{sessionId=\"__sessionId__\"})[__dayWindow__])@end())/60";

        String expr = "(lifetime(SLEEP_STAGES{sessionId=\"...\"}[__sessionWindow__]) - duration_over_time((SLEEP_STAGES{sessionId=\"...\"} == 0)[__sessionWindow__],30s) + (tlast_over_time((SLEEP_STAGES{sessionId=\"...\"} == 0)[__sessionWindow__]) - tlast_over_time((SLEEP_STAGES{sessionId=\"...\"} > 0)[__sessionWindow__]))) / 3600";

        String sol = "(tfirst_over_time((SLEEP_STAGES{sessionId=\"...\"} > 0)[__sessionWindow__])@end()-tfirst_over_time((SLEEP_STAGES{sessionId=\"...\"})[__sessionWindow__])@end())/60";

        String total_focus_hour_day = "sum without (sessionId)(lifetime(FOCUS_SCORE{profileId=\"...\",dayOfYear=\"...\",featureName=\"FOCUS\"}[__dayWindow__]))";
        String total_focus_hour_week = "sum without (sessionId, dayOfYear)(lifetime(FOCUS_SCORE{profileId=\"...\",weekOfYear=\"...\",featureName=\"FOCUS\"}[__weekWindow__])";

        String avg = "(sum without (sessionId) (sum_over_time(FOCUS_SCORE{profileId=\"...\",dayOfYear=\"...\"} [__dayWindow__]) >= 0)) / count(FOCUS_SCORE{profileId=\"...\",dayOfYear=\"...\"} [__dayWindow__])";
        //"lifetime({__name__=\"FOCUS_SCORE\",sessionId=\"5F5C38353C8A_1658764988000\"}[10d])"

//        System.out.println(encode(replace(expr)));
//        System.out.println(encode(replace(sol)));
//        System.out.println(encode(replace(total_focus_hour_day)));
        System.out.println(replace(avg));
        System.out.println(encode(replace(avg)));
    }

    private static String replace(String expr){
        return expr.replaceAll("profileId=\"...\"", "profileId=\"c2d46e49-9840-46de-89c6-b862d2c998f0\"")
                .replaceAll("dayOfYear=\"...\"", "dayOfYear=\"206\"")
                .replaceAll("featureName=\"...\"", "featureName=\"NAP\"")
                .replaceAll("__dayWindow__", "30d")
                .replaceAll("__weekWindow__", "30d")
                .replaceAll("weekOfYear=\"...\"", "weekOfYear=\"31\"")
                .replaceAll("__sessionWindow__", "20d")
                .replaceAll("sessionId=\"...\"", "sessionId=\"5F5C38353C8A_1658764988000\"");
    }
}
