package com.twitter.statistics.model;

import com.twitter.statistics.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TweetTest {

    private Tweet tweet;

    private String id;

    private String text;

    private TwitterUser twitterUser;

    private String createdAt;


    /**
     * initial setup to be done before the tests are run
     */
    @Before
    public void setup() {
        tweet = new Tweet();
        twitterUser = new TwitterUser();
        id = "1";
        text = "Abc";
        createdAt = "Sat Mar 25 10:52:18 +0000 2017";
    }

    /**
     * test the setter and getter methods of the class variables in the Tweet class
     *
     */
    @Test
    public void testGettersAndSetters() {
        tweet.setCreatedAt(createdAt);
        tweet.setText(text);
        tweet.setId(id);
        tweet.setTwitterUser(twitterUser);

        assertEquals(id,tweet.getId());
        assertEquals(text, tweet.getText());
        assertEquals(createdAt,tweet.getCreatedAt());
        assertEquals(1490439138,tweet.getCreatedAtEpoch());
    }

    @Test
    public void testEqualsHashCode_Symmetric() {
        Tweet x = TestUtil.createTweet("Sat Mar 25 10:52:18 +0000 2017", "1234", "Tue Feb 07 14:21:34 +0000 2012", 485725488);
        Tweet y = TestUtil.createTweet("Sat Mar 25 10:52:18 +0000 2017", "1234", "Tue Feb 07 14:21:34 +0000 2012", 485725488);
        Tweet z = TestUtil.createTweet("Sat Mar 25 10:52:22 +0000 2017", "4321", "Thu Aug 04 14:34:05 +0000 2016", 761208591);
        assertTrue(x.equals(y));
        assertFalse(z.equals(y));
        assertTrue(x.hashCode() == y.hashCode());
        assertFalse(z.hashCode() == y.hashCode());
    }




}
