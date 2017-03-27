package com.twitter.statistics.utils;

import com.twitter.statistics.model.Tweet;
import com.twitter.statistics.model.TwitterUser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test class to tes the Json Util class
 * @author Mithilesh Ravindran
 */
public class JSONUtilTest {

    private JSONUtil<Tweet> jsonUtil;

    private String json;

    /**
     * intial tasks to be completed before each test case.
     *
     * @throws IOException
     */
    @Before
    public void doBeforeEachTestCase() throws IOException {
        Tweet tweetWrapper = null;
        jsonUtil = new JSONUtil<>();
        json = TestUtil.getValidTweet();
    }

    /**
     * Test Case: To test the JSON Util class with a mock json string expected
     * to get converted into TWEET object
     *
     * @throws Exception
     */
    @Test
    public void testJsonUtil_Success() throws Exception {
        Tweet tweet =jsonUtil.convertToObject(json, Tweet.class);
        assertEquals("je_garciabozzo", tweet.getTwitterUser().getScreenName());
        assertEquals("Fri Mar 24 10:52:18 +0000 2017", tweet.getCreatedAt());
    }

    /**
     * Test Case: To test the JSON Util class with a mock json string expected
     * to get converted into TWITTERUSER object
     *
     * @throws Exception
     */
    @Test
    public void testJsonUtil_WithDifferentObject() throws Exception {
        JSONUtil<TwitterUser> jsonTwitterUserUtil = new JSONUtil<>();
        TwitterUser tweet =jsonTwitterUserUtil.convertToObject(json, TwitterUser.class);
        assertNull(tweet.getScreenName());
        assertNull(tweet.getName());
    }

}
