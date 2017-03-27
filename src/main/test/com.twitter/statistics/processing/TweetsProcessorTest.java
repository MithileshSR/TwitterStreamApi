package com.twitter.statistics.processing;

import com.twitter.statistics.model.Tweet;
import com.twitter.statistics.model.TwitterUser;
import com.twitter.statistics.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;

/**
 * Test Class to check the functionality of the TweetsProcessor
 *
 * @author Mithilesh Ravindran
 */
public class TweetsProcessorTest {

    private TweetsProcessor tweetsProcessor;

    private List<Tweet> tweets;


    @Before
    public void doBeforeEachTestCase() throws IOException {
        tweets = createListOfTweets();
        tweetsProcessor = new TweetsProcessor(tweets);
    }

    /**
     * Method to create List of Tweets for Testing
     *
     * @return List of Tweets
     */
    private List<Tweet> createListOfTweets() {
        List<Tweet> tweets = new ArrayList<>();
        Tweet tweetOne = TestUtil.createTweet("Sat Mar 25 10:52:18 +0000 2017", "1234", "Tue Feb 07 14:21:34 +0000 2012", 485725488);
        Tweet tweetTwo = TestUtil.createTweet("Sat Mar 25 10:52:22 +0000 2017", "4321", "Thu Aug 04 14:34:05 +0000 2016", 761208591);
        Tweet tweetThree = TestUtil.createTweet("Sat Mar 25 10:52:21 +0000 2017", "4322", "Thu Aug 04 14:34:05 +0000 2016", 761208591);
        Tweet tweetFour = TestUtil.createTweet("Sat Mar 25 10:52:23 +0000 2017", "3333", "Thu Aug 04 14:34:05 +0000 2016", 761208692);
        tweets.add(tweetOne);
        tweets.add(tweetTwo);
        tweets.add(tweetThree);
        tweets.add(tweetFour);
        return tweets;
    }


    /**
     * Test case : Success flow in message processing for sorting with author created date and messages created date
     *
     * @throws Exception
     */
    @Test
    public void testMessageProcessor_Success()  {
        SortedMap<TwitterUser, SortedSet<Tweet>> messageRetrive = tweetsProcessor.process();
        TwitterUser a = messageRetrive.firstKey();
        TwitterUser b = messageRetrive.lastKey();
    }


}
