package ai.earable.platform.common.data.risk.enums;

import lombok.Getter;

public enum HealthRiskSeverity {
    NORMAL(1),
    WARNING(2),
    EMERGENCY(3);

    @Getter
    private int priority;

    HealthRiskSeverity(int priority) {
        this.priority = priority;
    }

    /**
     * Compare priority of status ( 0 = equal, 1 is target greater than source, -1 is target less than source )
     */
    public static int comparePriority(HealthRiskSeverity source, HealthRiskSeverity target) {
        if (target.priority == source.priority) {
            return 0;
        }
        if (target.priority > source.priority) {
            return 1;
        } else {
            return -1;
        }
    }
}
