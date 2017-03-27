/**
 * @author danbrotherston
 * 
 * Use this class to print out tweets combined with their author to the console
 * in a tabular format.
 * 
 * The provided userMap must include users for all authors of tweets that are provided
 * later otherwise a null pointer exception will result.
 */

package com.twitter.statistics.view;

import com.google.common.collect.ImmutableList;
import com.twitter.statistics.model.Tweet;
import com.twitter.statistics.model.TwitterUser;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class TweetsPrinter {

    private final TableView view;

    private static final List<String> HEADER =
        ImmutableList.of("Message ID", "Creation Date", "Screen Name",
            "Full Name", "User Date", "User ID", "Tweet Text");

    // These are set based on the requirements of the field.
    private static final List<Integer> COLUMN_WIDTHS =
        ImmutableList.of(18, 18, 16, 20, 18, 18, 52);


    public TweetsPrinter() {
        view = new TableView(HEADER, COLUMN_WIDTHS);
    }

    /**
     * This method starts a new table, and fills it with all the objects to print.
     */
    public void print(TwitterUser user, SortedSet<Tweet> objects) {
       List<TwitterUser> userProcessed = new ArrayList<>();
        if (!userProcessed.contains(user))
            view.startTable();
        else
            userProcessed.add(user);

        objects.forEach(tweet -> {
            List<String> tweetRow = convertObjectToRow(tweet);
            view.printRow(tweetRow);
               });

    }


    protected List<String> convertObjectToRow(Tweet tweet) {
        TwitterUser user = tweet.getTwitterUser();
        List<String> tweetRow = ImmutableList.of(
            tweet.getId(),
            "" + tweet.getCreatedAtEpoch(),
            user.getScreenName(),
            user.getName(),
            "" + user.getCreatedAtEpoch(),
            String.valueOf(user.getId()),
            tweet.getText());

        return tweetRow;
    }
}
