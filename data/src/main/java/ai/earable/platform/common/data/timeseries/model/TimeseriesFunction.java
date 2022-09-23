package ai.earable.platform.common.data.timeseries.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum TimeseriesFunction {
    LIFETIME ("lifetime"),
    SUM_OVER_TIME ("sum_over_time"),
    AVG_OVER_TIME ("avg_over_time"),
    COUNT_OVER_TIME ("count_over_time"),
    TFIRST_OVER_TIME ("tfirst_over_time"),
    TLAST_OVER_TIME ("tlast_over_time");

    @Getter
    private final String function;

    TimeseriesFunction(String function) {
        this.function = function;
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "a");
        map.put("2", "b");

        Map<String, String> map1 = new HashMap<>(map);
        Map<String, String> map2 = new HashMap<>(map);

        map1.computeIfPresent("2", (s, s2) -> "c");
        map2.computeIfPresent("2", (s, s2) -> "d");

        System.out.println(map);
        System.out.println(map1);
        System.out.println(map2);
    }
}
