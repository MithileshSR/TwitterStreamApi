package com.twitter.statistics.view;

import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.PrintStream;
import java.util.List;

public class TableViewTest {
    @Mock
    PrintStream mockPrinter;
    @Captor
    ArgumentCaptor<String> captor;
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    List<String> easyTable = ImmutableList.of(
        "Hello         World        Column     ",
        "--------------------------------------",
        "Test          123          Easy       ");
    
    List<String> easyHeader = ImmutableList.of("Hello", "World", "Column");
    List<String> easyRow = ImmutableList.of("Test", "123", "Easy");
    List<Integer> easyColumnWidths = ImmutableList.of(12, 11, 11);
    List<List<String>> easyRows = ImmutableList.of(easyRow);

    public void testTable(List<String> expected, List<String> headers,
                          List<Integer> widths, List<List<String>> rows) {
        //mockPrinter = Mockito.mock(PrintStream.class, Mockito.withSettings().verboseLogging());
        TableView view = new TableView(headers, widths, mockPrinter);
        view.startTable();

        for (List<String> row : rows) {
            view.printRow(row);
        }

        Mockito.verify(mockPrinter, Mockito.times(expected.size())).println(captor.capture());

        Assert.assertEquals(expected, captor.getAllValues());
    }

    // TODO: Add more comprehensive testing here.
    // TODO: This could also be a parametric test.
    @Test
    public void testEasyTable() {
        testTable(easyTable, easyHeader, easyColumnWidths, easyRows);
    }
}
