package com.twitter.statistics.processing;

import com.twitter.statistics.model.Tweet;
import com.twitter.statistics.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import static org.junit.Assert.assertEquals;

/**
 * Junit to test the TweetsDeserializer class
 *
 * @author Mithilesh Ravindran
 */
public class TweetsDeserializerTest {

    private TweetsDeserializer tweetsDeserializer;

    private LinkedBlockingDeque<String> messageQueue;

    /**
     * intial tasks to be completed before each test case.
     *
     * @throws IOException
     */
    @Before
    public void doBeforeEachTestCase() throws IOException {

        Tweet tweetWrapper = null;
        String json = TestUtil.getValidTweet();
        messageQueue = new LinkedBlockingDeque<>();

        messageQueue.add(json);
        tweetsDeserializer = new TweetsDeserializer(messageQueue);
    }


    /**
     * Method to create a mock string JSON response from the Twitter Streaming API
     *
     * @return String holds the JSON value for the test
     * @throws IOException
     *//*
    private String getMockJsonString() throws IOException {
        Tweet tweetWrapper;
        InputStream inJson = Tweet.class.getResourceAsStream("/message.json");
        tweetWrapper = new ObjectMapper().readValue(inJson, Tweet.class);
        ObjectMapper objMapper = new ObjectMapper();
        return objMapper.writeValueAsString(tweetWrapper);
    }*/



    /**
     * Test case : SuccessFlow of Deserialization call method.
     * Added tweet conclude processing method since the method will run endlessly
     * if the keepRunning variable is not changed
     *
     * @throws Exception
     */
    @Test
    public void testDeserialization_Success() throws Exception {
        tweetsDeserializer.concludeProcessing();
        List<Tweet> msg = tweetsDeserializer.call();
        assertEquals(1, msg.size());
        assertEquals("je_garciabozzo", msg.get(0).getTwitterUser().getScreenName());
        assertEquals("Fri Mar 24 10:52:18 +0000 2017", msg.get(0).getCreatedAt());
    }

    /**
     * Test case : Method to check the deserialization failure for the one of the tweets
     * Added tweet conclude processing method since the method will run endlessly
     * if the keepRunning variable is not changed
     * @throws Exception
     */
    @Test
    public void testDeserialization_Fails() throws Exception {
        messageQueue.addLast("Hi");
        messageQueue.removeFirst();
        tweetsDeserializer.concludeProcessing();
        List<Tweet> msg = tweetsDeserializer.call();
        assertEquals(0, msg.size());
    }

    /**
     * Test case : Method to check the deserialization failure for the one of the tweets and getting passed for other tweet
     * Added tweet conclude processing method since the method will run endlessly
     * if the keepRunning variable is not changed
     * @throws Exception
     */
    @Test
    public void testDeserialization_RunsForAllTweets_SecondTweetFails() throws Exception {
        messageQueue.addLast("Hi");
        tweetsDeserializer.concludeProcessing();
        List<Tweet> msg = tweetsDeserializer.call();
        assertEquals(1, msg.size());
        assertEquals("je_garciabozzo", msg.get(0).getTwitterUser().getScreenName());
        assertEquals("Fri Mar 24 10:52:18 +0000 2017", msg.get(0).getCreatedAt());
    }
}
