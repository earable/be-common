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
        String expr = "(sum without (sessionId,dayOfYear,featureName) ((lifetime(SLEEP_STAGES{profileId=\"...\",weekOfYear=\"...\"} [__weekWindow__]) - duration_over_time((SLEEP_STAGES{profileId=\"...\",weekOfYear=\"...\"} == 0)[__weekWindow__],30s) + (tlast_over_time((SLEEP_STAGES{profileId=\"...\",weekOfYear=\"...\"} == 0)[__weekWindow__]) - tlast_over_time((SLEEP_STAGES{profileId=\"...\",weekOfYear=\"...\"} > 0)[__weekWindow__]))) / 3600) / count(SLEEP_STAGES{profileId=\"...\",weekOfYear=\"...\"} [__weekWindow__]))";

        expr = expr.replaceAll("profileId=\"...\"", "profileId=\"316e0ecb-2c8e-437c-8f27-3ffe7c27b47f\"")
            .replaceAll("dayOfYear=\"...\"", "dayOfYear=\"194\"")
            .replaceAll("featureName=\"...\"", "featureName=\"NAP\"")
            .replaceAll("__dayWindow__", "10d")
                .replaceAll("__weekWindow__", "20d")
                .replaceAll("weekOfYear=\"...\"", "weekOfYear=\"29\"");
        System.out.println(encode(expr));
    }
}
