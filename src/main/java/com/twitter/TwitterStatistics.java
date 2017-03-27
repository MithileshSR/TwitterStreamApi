package com.twitter;

import com.google.api.client.http.*;
import com.twitter.statistics.model.Tweet;
import com.twitter.statistics.model.TwitterUser;
import com.twitter.statistics.oauth.TwitterAuthenticationException;
import com.twitter.statistics.oauth.TwitterAuthenticator;
import com.twitter.statistics.view.TweetsPrinter;
import com.twitter.statistics.processing.TweetsDeserializer;
import com.twitter.statistics.processing.TweetsProcessor;
import com.twitter.statistics.processing.TweetsRetriever;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiConsumer;

public class TwitterStatistics {

    private static final Logger logger = LoggerFactory.getLogger(TwitterStatistics.class);

    private static final String TRACKING_URL = "https://stream.twitter.com/1.1/statuses/filter.json?track=";

    private HttpRequestFactory requestFactory;

    private ExecutorService messageReaderPool = Executors.newSingleThreadExecutor();

    private ExecutorService messageDeserializerPool = Executors.newSingleThreadExecutor();

    private List<Tweet> tweets = new ArrayList<>();

    private TweetsPrinter tweetsPrinter;

    public void authenticate() throws TwitterAuthenticationException {

        TwitterAuthenticator twitterAuthenticator = new TwitterAuthenticator(System.out, "RLSrphihyR4G2UxvA0XBkLAdl",
                "FTz2KcP1y3pcLw0XXMX5Jy3GTobqUweITIFy4QefullmpPnKm4");
        requestFactory = twitterAuthenticator.getAuthorizedHttpRequestFactory();
    }

    public void startTracking(String stringToTrack, int maxMessages, int trackingTimeout) throws IOException {

        logger.info("Starting tracking on: {}", stringToTrack);
        logger.info("Tracking for {} tweets or {} seconds", maxMessages, trackingTimeout);

        GenericUrl url = new GenericUrl(TRACKING_URL + stringToTrack);
        HttpRequest request = requestFactory.buildGetRequest(url);
        HttpResponse httpResponse = request.execute();

        if (httpResponse.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {

            startTracking(httpResponse.getContent(), maxMessages, trackingTimeout);

        } else {
            logger.error("Http response code: {}", httpResponse.getStatusCode());
            logger.error("Error message: {}", httpResponse.getStatusMessage());
        }

        httpResponse.disconnect();
        logger.info("Tracking session disconnected!");
    }

    private void startTracking(InputStream source, int maxMessages, int trackingTimeout) {

        LinkedBlockingQueue<String> messageQueue = new LinkedBlockingQueue<>(maxMessages);

        TweetsRetriever tweetsRetriever = new TweetsRetriever(source, messageQueue, maxMessages, trackingTimeout);
        Future<Boolean> messageReaderResult = messageReaderPool.submit(tweetsRetriever);

        TweetsDeserializer tweetsDeserializer = new TweetsDeserializer(messageQueue);
        Future<List<Tweet>> messageDeserializationResult = messageDeserializerPool.submit(tweetsDeserializer);

        try {
            boolean receivedAllMessages = messageReaderResult.get();

            if (receivedAllMessages) {

                tweetsDeserializer.concludeProcessing();
                List<Tweet> deSerializedTweets = messageDeserializationResult.get();

                tweets.addAll(deSerializedTweets);
            }

        } catch (Exception e) {
            logger.error("Error while tracking. Aborting tracking!", e);
        } finally {

            logger.info("Shutting down TweetsRetriever");
            messageReaderPool.shutdown();

            logger.info("Shutting down TweetsDeserializer");
            messageDeserializerPool.shutdown();
        }
    }

    public void printMessagesGroupedByAuthor() {

        TweetsProcessor messageProcessor = new TweetsProcessor(tweets);
        SortedMap<TwitterUser, SortedSet<Tweet>> sortedMessages = messageProcessor.process();

        sortedMessages.forEach(printAuthorAndMessages);

    }



    private BiConsumer<TwitterUser, SortedSet<Tweet>> printAuthorAndMessages = (author, authorMessages) -> {

        System.out.println("---------------------------");
        System.out.println("Messages Grouped By Authors");
        System.out.println("---------------------------");
        tweetsPrinter = new TweetsPrinter();
        tweetsPrinter.print(author,authorMessages);

    };
}
