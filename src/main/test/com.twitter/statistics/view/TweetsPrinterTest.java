package com.twitter.statistics.view;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Test Class to Test the Tweets Printer
 *
 * @author Mithilesh Ravindran
 */
public class TweetsPrinterTest {

    private TweetsPrinter tweetsPrinter;


    /**
     * To do before running other tests
     */
    @Before
    public void doBeforeEachTestCase() throws IOException {
        tweetsPrinter = new TweetsPrinter();
    }

    /**
     * Test case : Success flow in Tweets Printer class
     *
     */
    @Test
    public void testMessageProcessor_Success()  {
        //Todo
    }
}
