package com.twitter.statistics.utils;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * A test class to check the DateTimeUtil class
 * 
 * @author Mithilesh Ravindran
 *
 */
public class DateTimeUtilTest {

    private DateTimeUtil dateTimeUtil;

    /**
     * Test Case: To test the Date Util class with a pattern from tweet response and its respective pattern
     *
     */
    @Test
    public void testDateUtil_Success()  {
        long epochTime = DateTimeUtil.toEpochTime("Sat Mar 25 10:52:18 +0000 2017","EEE MMM dd HH:mm:ss Z yyyy");
        assertEquals(1490439138,epochTime);
    }

    /**
     * Test Case: To test the Date Util with different string pattern and string
     *
     */
    @Test
    public void testDateUtil_WithDifferentPattern()  {
        long epochTime = DateTimeUtil.toEpochTime("2001-07-04T12:08:56","yyyy-MM-dd'T'HH:mm:ss");
        assertEquals(994248536,epochTime);
    }

    /**
     * Test Case: To test the Date Util with different string pattern and unmatched string
     *
     */
    @Test
    public void testDateUtil_WithDifferentPatternAndString()  {
        long epochTime = DateTimeUtil.toEpochTime("Sat Mar 25 10:52:18 +0000 2017","yyyy-MM-dd'T'HH:mm:ss");
        assertEquals(-1,epochTime);
    }

    /**
     * Test Case: To test the Date Util with different string of  datepattern and datestring alone
     * and get fails since the time is not available
     *
     */
    @Test
    public void testDateUtil_WithDifferentDateOnlyPattern()  {
        long epochTime = DateTimeUtil.toEpochTime("1989-04-30","yyyy-MM-dd");
        assertEquals(-1,epochTime);
    }

}
