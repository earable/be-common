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
        String expr = "sum without (sessionId)((lifetime(SLEEP_STAGES{dayOfYear=\"194\",featureName=\"NAP\"}[2d]) - duration_over_time((SLEEP_STAGES{dayOfYear=\"194\",featureName=\"NAP\"} == 0)[2d],30s) + (tlast_over_time((SLEEP_STAGES{dayOfYear=\"194\",featureName=\"NAP\"} == 0)[2d]) - tlast_over_time((SLEEP_STAGES{dayOfYear=\"194\",featureName=\"NAP\"} > 0)[2d]))) / 3600)";
        System.out.println(encode(expr.replace("__sessionId__", "6089203057A6_1657702437000")));
        System.out.println(encode("tfirst_over_time((SLEEP_STAGES{sessionId=\"6089203057A6_1657702437000\"} == 1)[2d])"));
    }
}
