/**
 * @author Mithilesh Ravindran
 * This class is responsible for wordwrapping strings.  It takes a string,
 * and a length, and wraps the string on word boundaries as much as possible
 * and outputs as short a List of Strings as possible such that all the
 * Strings are not longer than the column size given, and as much as possible
 * are wrapped on the word boundary.  It also begins by splitting strings
 * on newline characters.
 * 
 * Finally, the variable WRAPPING_SIZE configures how far the wrapper will search
 * for a whitespace character before giving up and breaking in the middle of a word.
 */

package com.twitter.statistics.view;

import java.util.ArrayList;
import java.util.List;

public class StringWrapper {
    /**
     * How far should the wrapper look backwards for a whitespace character before
     * giving up and breaking the string at the column boundary in the middle of a
     * word.  It functions as a fraction of the column size and should be bounded
     * between 0 (no wrapping) and 1 (wrap unless word fills the line);
     */
    public static double WRAPPING_SIZE = 0.35;

    /**
     * What character should the wrapper use to indicate a word has been broken
     * outside of the space boundary.
     * 
     * Please note, this is not proper lexical breaking, and could result in 'ugly'
     * breaks such as:
     * 
     *   |  this is a long string of textua-  |
     *   |  l data.                           |
     * 
     * A proper algorithm would take into account the structure of words that are
     * broken, but we are just looking for a quick and dirty solution here.
     */
    public static char WRAPPING_CHAR = '-';

    private final int columnSize;

    public StringWrapper(int columnSize) {
        this.columnSize = columnSize;
    }

    private int getWrappingSize() {
        int wrapSize = (int)Math.round(((double)columnSize * WRAPPING_SIZE));

        if (wrapSize < 0 || wrapSize > columnSize) {
            throw new RuntimeException("Invalid WRAPING_SIZE constant: "
                + WRAPPING_SIZE + " wrap size must be bounded between 0 and 1.");
        }

        return wrapSize;
    }

    /**
     * @param s The string to wrap.
     * @returns A list of strings, representing each resulting
     * line that should be written when s is printed.
     * The list is guaranteed to contain no line breaks,
     * and for each string to be less than columnSize in legnth.
     */
    public List<String> wrapString(String s) {
        List<String> wrappedStrings = new ArrayList<String>();
        String[] strings = s.split("\\r?\\n");

        for (String line: strings) {
            line = line.trim();
            while (line.length() > columnSize) {
                int breakPosition = columnSize;
                while (!Character.isWhitespace(line.charAt(breakPosition))) {
                    // Find the break position by searching backwards in the string
                    // for a whitespace character, and if finding nothing before
                    // the wrapSize then giving up and selecting the column size
                    // (minus 1 for the break character).
                    breakPosition--;
                    if (breakPosition < getWrappingSize()) {
                        breakPosition = columnSize;
                        line = line.substring(0, breakPosition - 1)
                            + WRAPPING_CHAR 
                            + line.substring(breakPosition - 1);
                        break;
                    }
                }   

                wrappedStrings.add(line.substring(0, breakPosition).trim());
                line = line.substring(breakPosition);
                line = line.trim();
            }

            wrappedStrings.add(line);
        }

        return wrappedStrings;
    }
}
