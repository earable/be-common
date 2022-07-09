package ai.earable.platform.common.data.feature.dto;

import ai.earable.platform.common.data.feature.model.SessionEvent;
import lombok.*;

/**
 * Created by BinhNH on 3/28/2022
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionOperationNotification {
    private String userId;
    private String featureName;
    private String profileId;
    private String deviceId;
    private String sessionId;
    private SessionEvent event;

    /**
     * The clientTimestamp at the moment of session started in client timezone
     */
    private long clientTimestamp;

    /**
     * The client timezone at the moment of session started
     */
    private String timezone;

    /**
     * The year at the moment of session started in client timezone
     * Note: In the case which session pass through two years, this year value is the before year.
     * This attribute supports client to query timeseries by year.
     */
    private int year;

    /**
     * The month of the year at the moment of session started in client timezone
     * This attribute supports client to query timeseries by year.
     */
    private int monthOfYear;

    /**
     * The week of the year at the moment of session started in client timezone
     * This attribute supports client to query timeseries by year as well as backend queries to collect performance
     * report.
     */
    private int weekOfYear;

    /**
     * The day of the year at the moment of session started in client timezone
     * This attribute supports client to query timeseries by year as well as backend queries to collect performance
     * report.
     */
    private int dayOfYear;
}
