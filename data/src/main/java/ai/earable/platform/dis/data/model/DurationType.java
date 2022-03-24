package ai.earable.platform.dis.data.model;

import lombok.Getter;

/**
 * Created by BinhNH on 3/19/22
 */
@Getter
public enum DurationType {
    MILLISECOND ("ms"),
    SECOND ("s"),
    HOUR ("h"),
    DAY ("d"),
    WEEK ("w"),
    YEAR ("y");

    private final String label;

    DurationType(String label) {
        this.label = label;
    }
}
