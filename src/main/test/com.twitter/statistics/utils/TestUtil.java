package com.twitter.statistics.utils;

import com.twitter.statistics.model.Tweet;
import com.twitter.statistics.model.TwitterUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Test Util class to create a common test object needed across the test classes
 *
 * @author Mithilesh Ravindran
 */
public class TestUtil {

    /**
     * Method to get a mocked valid tweet as string from the  stored tweet from the classpath
     * @return String holding the mock String
     * @throws FileNotFoundException
     */
    public static String getValidTweet() throws FileNotFoundException {
        return getResource("/message.json").replaceAll("\n", "");
    }

    /**
     * Method to get the tweet from the stored resource from the classpath
     * @param resourcePath
     * @return
     * @throws FileNotFoundException
     */
    public static String getResource(String resourcePath) throws FileNotFoundException {
        File file = new File(TestUtil.class.getClass().getResource(resourcePath).getFile());
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("\\Z");
        return scanner.next();
    }

    /**
     * Method to create Tweet object
     *
     * @param tweetCreatedDate of type String
     * @param tweetId          of type String
     * @param userCreatedDate  of type String
     * @param userId           of type long
     * @return Tweet Object
     */
    public static Tweet createTweet(String tweetCreatedDate, String tweetId, String userCreatedDate, long userId) {
        Tweet tweetOne = new Tweet();
        tweetOne.setTwitterUser(createUser(userCreatedDate, userId));
        tweetOne.setCreatedAt(tweetCreatedDate);
        tweetOne.setId(tweetId);
        tweetOne.setText("Hi Bieber");
        return tweetOne;
    }

    /**
     * Method to create TwitterUser Object
     *
     * @param userCreatedDate of type String
     * @param userId          of type long
     * @return TwitterUser
     */
    public static TwitterUser createUser(String userCreatedDate, long userId) {
        TwitterUser twitterUser = new TwitterUser();
        twitterUser.setCreatedAt(userCreatedDate);
        twitterUser.setId(userId);
        twitterUser.setName("Blabalah");
        twitterUser.setScreenName("Blabalah");
        return twitterUser;
    }
}
