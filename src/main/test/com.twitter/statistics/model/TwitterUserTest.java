package com.twitter.statistics.model;

import com.twitter.statistics.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TwitterUserTest {

    private TwitterUser twitterUser;

    private long id;

    private String screenName;

    private String name;

    private String createdAt;


    /**
     * initial setup to be done before the tests are run
     */
    @Before
    public void setup() {
        twitterUser = new TwitterUser();
        id = 1;
        screenName = "Abc";
        name = "Mithilesh";
        createdAt = "Sat Mar 25 10:52:18 +0000 2017";
    }

    /**
     * test the setter and getter methods of the class variables in the TwitterUser class
     *
     */
    @Test
    public void testGettersAndSetters() {
        twitterUser.setCreatedAt(createdAt);
        twitterUser.setScreenName(screenName);
        twitterUser.setName(name);
        twitterUser.setId(id);

        assertEquals(id,twitterUser.getId());
        assertEquals(name, twitterUser.getName());
        assertEquals(screenName, twitterUser.getScreenName());
        assertEquals(createdAt,twitterUser.getCreatedAt());
        assertEquals(1490439138,twitterUser.getCreatedAtEpoch());
    }

    @Test
    public void testEqualsHashCode_Symmetric() {
        TwitterUser x = TestUtil.createUser("Tue Feb 07 14:21:34 +0000 2012", 485725488);
        TwitterUser y = TestUtil.createUser("Tue Feb 07 14:21:34 +0000 2012", 485725488);
        TwitterUser z = TestUtil.createUser("Thu Aug 04 14:34:05 +0000 2016", 761208591);
        assertTrue(x.equals(y));
        assertFalse(z.equals(y));
        assertTrue(x.hashCode() == y.hashCode());
        assertFalse(z.hashCode() == y.hashCode());
    }



}
