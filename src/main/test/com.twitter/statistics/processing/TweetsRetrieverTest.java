package com.twitter.statistics.processing;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static org.junit.Assert.assertEquals;

/**
 * Test Class to the TweetsRetriever
 * 
 * @author Mithilesh Ravindran
 *
 */
public class TweetsRetrieverTest  {

    private TweetsRetriever tweetsRetriever;

    BlockingQueue<String> queue;

    InputStream stream;

    @Before
    public void doBeforeEachTestCase() throws IOException {
        stream = TweetsRetrieverTest.class.getResourceAsStream("/messages.txt");
        queue = new LinkedBlockingDeque<>();
        int maxTweets = 10;
        int trackingTimeOut = 20;
        tweetsRetriever = new TweetsRetriever(stream, queue, maxTweets, trackingTimeOut );
    }

    /**
     * Test case : Success & Failure Flow of Tweets call method.
     * From file adding two messages to stream and expecting to run th retriver for
     * 20 seconds or till up to 10 max tweets
     * Since the files being loaded from file we  get excpetion from the third line of the input source
     * and abruptly stops the thread
     *
     *
     * @throws Exception
     */
    @Test
    public void testDeserialization_Success() throws Exception {
        boolean messageRetrive = tweetsRetriever.call();
        assertEquals(true,messageRetrive);
    }

    /**
     * Test case : SuccessFlow of TweetsRetriever call method.
     * From file adding two messages to stream and expecting to run th retriver for
     * 1 seconds or till up to 1 max tweets
     * Since the files has two tweets added but only one of the tweet is being processed that can been seen in the logs
     *
     * @throws Exception
     */
    @Test
    public void testDeserialization_SuccessWithDifferentTimeOut() throws Exception {
        int maxTweets = 1;
        int trackingTimeOut =1;
        tweetsRetriever = new TweetsRetriever(stream, queue, maxTweets, trackingTimeOut );
        boolean messageRetrive = tweetsRetriever.call();
        assertEquals(true,messageRetrive);
    }


}
