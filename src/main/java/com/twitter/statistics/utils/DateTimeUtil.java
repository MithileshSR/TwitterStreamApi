package com.twitter.statistics.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * A utility class to convert string date-time to epoch time
 * 
 * @author Mithilesh Ravindran
 *
 */
public class DateTimeUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

    public static long toEpochTime(String timeString, String pattern) {

        long epochTime = -1;

        try {

            TemporalAccessor temporalAccessor = DateTimeFormatter.ofPattern(pattern).parse(timeString);
            LocalDateTime localDateTime = LocalDateTime.from(temporalAccessor);

                epochTime = localDateTime.toEpochSecond(ZoneOffset.UTC);

        } catch (Exception e) {
            logger.error("Unable to convert into epoch time. Time: '{}', Pattern: '{}'", timeString, pattern, e);
        }

        return epochTime;
    }
}
