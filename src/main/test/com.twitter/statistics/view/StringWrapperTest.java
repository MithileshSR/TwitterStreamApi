package com.twitter.statistics.view;

import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class StringWrapperTest {
    StringWrapper wrapper;

    String noWrapping = "Abc Def dfAbc Def dfasd";
    List<String> expectedNoWrapping = ImmutableList.of("Abc Def dfAbc Def dfasd");

    String wrapping = "Abc Def dfAbc Def dAbcdd Def dfAbc Def df";
    List<String> expectedWrapping =
        ImmutableList.of("Abc Def dfAbc Def", "dAbcdd Def dfAbc Def df");

    String wrappingMore = "Abc Def dfAbc Def dfAbcdd Def dfAbc Def df";
    List<String> expectedWrappingMore =
        ImmutableList.of("Abc Def dfAbc Def", "dfAbcdd Def dfAbc Def", "df");

    String wrappingColumn = "Abcd efddfAbcdDefddfAbcd efddfAbcdDefddf";
    List<String> expectedWrappingColumn =
        ImmutableList.of("Abcd efddfAbcdDefddfAb-", "cd efddfAbcdDefddf");

    String multiLineWrapping = "Abcd\nefddfAbcdDefddfAbcd efddfAbcdDefddfasdfdssss sdfa asdfa asdf adsf sf\n" +
        "Abc Def dfAbc Def dfAbcdd Def dfAbc Def df";
    List<String> expectedMultiLineWrapping =
        ImmutableList.of("Abcd",
            "efddfAbcdDefddfAbcd", "efddfAbcdDefddfasdfdss-", "ss sdfa asdfa asdf adsf", "sf",
            "Abc Def dfAbc Def", "dfAbcdd Def dfAbc Def", "df");

    @Before
    public void setup() {
        // TODO: Write tests for extreme column sizes
        wrapper = new StringWrapper(23);
    }

    // TODO: This could be a parametric test.

    public void testWrapper(String string, List<String> expectedWrapping) {
        List<String> wrappedStrings = wrapper.wrapString(string);
        Assert.assertEquals(expectedWrapping, wrappedStrings);
    }

    @Test
    public void testNoWrapping() {
        testWrapper(noWrapping, expectedNoWrapping);
    }

    @Test
    public void testWrapping() {
        testWrapper(wrapping, expectedWrapping);
    }

    @Test
    public void testWrappingMore() {
        testWrapper(wrappingMore, expectedWrappingMore);
    }

    @Test
    public void testWrappingColumn() {
        testWrapper(wrappingColumn, expectedWrappingColumn);
    }

    @Test
    public void testMultiLineWrapping() {
        testWrapper(multiLineWrapping, expectedMultiLineWrapping);
    }
}
