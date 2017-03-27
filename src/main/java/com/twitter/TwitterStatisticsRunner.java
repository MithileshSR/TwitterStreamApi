package com.twitter;

import com.twitter.statistics.oauth.TwitterAuthenticationException;

import java.io.IOException;

/**
 * Application entry point
 * 
 * @author Mithilesh Ravindran
 *
 */
public class TwitterStatisticsRunner {

    public static void main(String[] args) throws TwitterAuthenticationException, IOException {

        TwitterStatistics application = new TwitterStatistics();

        application.authenticate();

        String tweetWordsToTrack = System.getProperty("tracking.keywords", "bieber");
        int thresholdMessages = getIntProperty("tracking.limit", 5);
        int maxTrackingTime = getIntProperty("tracking.timeoutseconds", 3);

        application.startTracking(tweetWordsToTrack, thresholdMessages, maxTrackingTime);
        application.printMessagesGroupedByAuthor();
    }

    private static int getIntProperty(String propertyName, int defaultValue) {

        String propertyValue = System.getProperty(propertyName, String.valueOf(defaultValue));

        try {
            return Integer.parseInt(propertyValue);
        } catch (Exception e) {
        }

        return defaultValue;
    }
}
