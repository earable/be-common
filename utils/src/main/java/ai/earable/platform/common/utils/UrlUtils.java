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
        System.out.println(encode("(lifetime(SLEEP_STAGES{sessionId=\"5F6138353B8A_1649821060000\"}[2d]) - duration_over_time((SLEEP_STAGES{sessionId =\"5F6138353B8A_1649821060000\"} == 4)[2d],30s) + (tlast_over_time((SLEEP_STAGES{sessionId =\"5F6138353B8A_1649821060000\"} == 4)[2d]) - tlast_over_time((SLEEP_STAGES{sessionId =\"5F6138353B8A_1649821060000\"} != 0 AND SLEEP_STAGES{sessionId =\"5F6138353B8A_1649821060000\"} != 4)[2d]))) / 3600"));
    }
}
