package com.twitter.statistics.processing;

import com.twitter.statistics.model.TwitterUser;
import com.twitter.statistics.model.Tweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Consumer;

/**
 * It processes retrieved tweets to group them by authors. It also sorts the
 * tweets in chronological order of author creation time and message creation
 * time.
 * 
 * @author Mithilesh Ravindran
 *
 */
public class TweetsProcessor {

    private static final Logger logger = LoggerFactory.getLogger(TweetsProcessor.class);

    private List<Tweet> tweets;

    public TweetsProcessor(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public SortedMap<TwitterUser, SortedSet<Tweet>> process() {

        SortedMap<TwitterUser, SortedSet<Tweet>> messagesGroupdByAuthors = new TreeMap<>(userSorter);

        Consumer<Tweet> groupByAuthor = (message) -> {

            logger.debug("Processing message: {}", message.getId());

            TwitterUser twitterUser = message.getTwitterUser();

            if (!messagesGroupdByAuthors.containsKey(twitterUser)) {
                messagesGroupdByAuthors.put(twitterUser, new TreeSet<>(tweetSorter));
            }

            messagesGroupdByAuthors.get(twitterUser).add(message);
        };

        tweets.forEach(groupByAuthor);

        return messagesGroupdByAuthors;
    }

    private Comparator<Tweet> tweetSorter = (tweetOne, tweetTwo) -> {

        if (tweetTwo == null) {
            return -1;
        }
        return (int)(tweetOne.getCreatedAtEpoch() - tweetTwo.getCreatedAtEpoch());

    };

    private Comparator<TwitterUser> userSorter = (userOne, userTwo) -> {

        if (userTwo == null) {
            return -1;
        }

       return (int) (userOne.getCreatedAtEpoch() - userTwo.getCreatedAtEpoch());

    };
}
