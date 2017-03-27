/**
 * @author Mithilesh Ravindran
 * This class renders row and column data on the screen, allowing for some columns to have wrapping.
 * 
 * The class can be used with as many tables of the same format as wished, just call "startTable" to
 * restart printing the header.
 * 
 * Then call the row method repeatedly with data to fill the table.  No printing should
 * occur between any of the calls to the table methods or the table may be disrupted.
 */

package com.twitter.statistics.view;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class TableView {

    public static String COLUMN_SEPARATOR = "  ";

    private final List<String> headers;
    private final List<Integer> columnWidths;
    private final List<StringWrapper> wrappers;
    private final PrintStream printer;
    private final int numColumns;

    public TableView(List<String> headers, List<Integer> columnWidths) {
        assert headers.size() == columnWidths.size();
        this.headers = headers;
        this.columnWidths = columnWidths;
        this.printer = System.out;
        this.wrappers = ImmutableList.copyOf(buildWrappers(columnWidths));
        this.numColumns = columnWidths.size();
    }

    public TableView(List<String> headers, List<Integer> columnWidths, PrintStream printer) {
        assert headers.size() == columnWidths.size();
        this.headers = headers;
        this.columnWidths = columnWidths;
        this.printer = printer;
        this.wrappers = ImmutableList.copyOf(buildWrappers(columnWidths));
        this.numColumns = columnWidths.size();
    }
    
    private List<StringWrapper> buildWrappers(List<Integer> columnWidths) {
        List<StringWrapper> wrappers = new ArrayList<StringWrapper>(columnWidths.size());
        for (Integer i : columnWidths) {
            wrappers.add(new StringWrapper(i));
        }

        return wrappers;
    }

    /**
     * Gets the printing width of the table in characters.
     */
    public int getTableWidth() {
        int width = 0;

        for (Integer i : columnWidths) {
            width += i;
        }

        // Plus add space for between columns, 
        // 3 characters (*space* *bar* *space*)
        // and for one less column (because it's between columns)
        width += (COLUMN_SEPARATOR.length() * (numColumns - 1));

        return width;
    }

    public int getNumColumns() {
        return numColumns;
    }

    /**
     * Calling this method will render the table header.  It should be called first, and only once.
     */
    public void startTable() {
        printCells(headers);
        printer.println(StringUtils.repeat('-', getTableWidth()));
    }

    /**
     * This method prints one row of a table given the entries in a list
     * of strings.
     */
    public void printCells(List<String> rowEntries) {
        List<List<String>> columns = new ArrayList<List<String>>(numColumns);
        for (int i = 0; i < numColumns; i++) {
            columns.add(wrappers.get(i).wrapString(rowEntries.get(i)));
        }

        List<String> lines = transpose(columns);

        for (String s : lines) {
            printer.println(s);
        }
    }

    /**
     * This method prints one row of a table given the entries in a list
     * of strings.
     */
    public void printRow(List<String> rowEntries) {
        this.printCells(rowEntries);
        printer.println();
    }

    /**
     * Transpose a list of columns, where the columns are a list
     * of strings for a wrapped text, into a single overall list
     * of strings to print the whole row.
     */
    private List<String> transpose(List<List<String>> columns) {
        // We must have the right number of columns
        assert columns.size() == numColumns;

        // Compute the maximum height of any cell.
        int maxLines = 0;
        for (List<String> column : columns) {
            maxLines = (int)Math.max(maxLines, column.size());
        }

        List<String> transposedColumns = new ArrayList<String>(maxLines);

        for (int i = 0; i < maxLines; i++) {
            List<String> columnsForRow = new ArrayList<String>(numColumns);

            for (int j = 0; j < columns.size(); j++) {
                List<String> column = columns.get(j);
                int columnWidth = columnWidths.get(j);
                if (i >= column.size()) {
                    // If there is no more data for this column, just use spaces.
                    columnsForRow.add(StringUtils.repeat(' ', columnWidth));
                } else {
                    // If there is more data, pad the data to right width and add it.
                    columnsForRow.add(StringUtils.leftPad(column.get(i), columnWidth, ' '));
                }
            }

            transposedColumns.add(StringUtils.join(columnsForRow, COLUMN_SEPARATOR));
        }

        return transposedColumns;
    }
}
