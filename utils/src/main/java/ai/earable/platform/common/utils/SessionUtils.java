package ai.earable.platform.common.utils;

import ai.earable.platform.common.data.session.model.Session;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static ai.earable.platform.common.data.session.SessionConstant.*;
import static ai.earable.platform.common.data.session.SessionConstant.REMSLEEPTIME;

/**
 * @author DungNT
 * @date 15/01/2024
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionUtils {

    public static Session populateSession(Session s) {
        Map<String, String> metaData = s.getMetadata();

        if (metaData.containsKey(DEEPSLEEPBOOTING)) {
            s.setDeepSleepBooting((int) Float.parseFloat(metaData.get(DEEPSLEEPBOOTING)));
        }
        Integer duration = (int) (Double.parseDouble(metaData.get(ENDED_TIME)) - Double.parseDouble(metaData.get(STARTED_TIME)));
        s.setDuration(duration);
        if (metaData.containsKey(SLEEPRATING)) {
            s.setSleepRating(Integer.parseInt(metaData.get(SLEEPRATING)));
        }
        if (metaData.containsKey(TOTALUNDEFINEDTIME)) {
            Integer totalUndefinedTime = Integer.parseInt(metaData.get(TOTALUNDEFINEDTIME));
            Float percentage = (float) Math.round(totalUndefinedTime * 100D / duration);
            s.setTotalUndefinedTime(totalUndefinedTime);
            s.setTotalUndefinedPercentage(percentage);
        }
        if (metaData.containsKey(BATTERYCONSUMED)) {
            Integer batteryConsumed = Integer.parseInt(metaData.get(BATTERYCONSUMED));
            s.setBatteryConsumed(batteryConsumed);
        }
        if (metaData.containsKey(ENDSESSIONBY)) {
            s.setEndSessionBy(metaData.get(ENDSESSIONBY));
        }
        if (metaData.containsKey(TIMETOFALLASLEEP)) {
            s.setTimeToFallAsSleep(Integer.parseInt(s.getMetadata().get(TIMETOFALLASLEEP)));
        }
        if (metaData.containsKey(SLEEPEFFICIENCY)) {
            s.setSleepEfficiency(Float.parseFloat(metaData.get(SLEEPEFFICIENCY)));
        }
        if (metaData.containsKey(TOTALSLEEPTIME)) {
            s.setTotalSleepTime(Integer.parseInt(metaData.get(TOTALSLEEPTIME)));
        }
        if (metaData.containsKey(DEEPSLEEPTIME)) {
            s.setDeepSleepTime(Integer.parseInt(metaData.get(DEEPSLEEPTIME)));
        }

        if (metaData.containsKey(REMSLEEPTIME)) {
            s.setRemSleepTime(Integer.parseInt(metaData.get(REMSLEEPTIME)));
        }
        if (metaData.containsKey(ENDED_TIME)) {
            s.setSessionEndedTime((long) Double.parseDouble(metaData.get(ENDED_TIME)));
        }

        if (metaData.containsKey(SERIALNO)) {
            s.setSerialNo(metaData.get(SERIALNO));
        }
        if (metaData.containsKey(TOTALSLEEPTIME) && metaData.containsKey(DEEPSLEEPTIME) && metaData.containsKey(REMSLEEPTIME)) {

            Integer lightSleepTime = Integer.parseInt(metaData.get(TOTALSLEEPTIME)) - Integer.parseInt(metaData.get(DEEPSLEEPTIME)) - Integer.parseInt(metaData.get(REMSLEEPTIME));

            s.setLightSleepTime(lightSleepTime);
        }
        s.setIsDataProcessed(true);
        return s;
    }
}
