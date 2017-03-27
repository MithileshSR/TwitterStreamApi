package com.twitter.statistics.processing;

import com.twitter.statistics.model.Tweet;
import com.twitter.statistics.utils.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * A Callable implementation to monitor messageQueue and de-serialize messages
 * retrieved from twitter into Java model.
 * 
 * If no messages are available in messageQueue, it waits for new messages to be
 * available in messageQueue.
 * 
 * @author Mithilesh Ravindran
 *
 */
public class TweetsDeserializer implements Callable<List<Tweet>> {

    private static final Logger logger = LoggerFactory.getLogger(TweetsDeserializer.class);

    private boolean keepRunning = true;

    private BlockingQueue<String> messageQueue;

    private JSONUtil<Tweet> jsonUtil = new JSONUtil<>();

    public TweetsDeserializer(BlockingQueue<String> messageQueue) {
        this.messageQueue = messageQueue;
    }

    @Override
    public List<Tweet> call() throws Exception {

        ArrayList<Tweet> tweets = new ArrayList<>();

        logger.info("Started de-serializing received tweets");

        while (!messageQueue.isEmpty() || keepRunning) {

            try {

                String jsonString = messageQueue.poll(1, TimeUnit.SECONDS);

                if (jsonString != null) {

                    Tweet tweet = jsonUtil.convertToObject(jsonString, Tweet.class);

                    if (tweet.getId() == null || tweet.getId().trim().isEmpty()) {
                        logger.warn("Non status tweet received. Skipping tweet - {}", jsonString);
                        continue;
                    }

                    tweets.add(tweet);
                    logger.debug("Tweet de-serialized: {}", tweet.getId());
                }

            } catch (Throwable t) {
                logger.error("Exception while de-serializing message", t);
            }
        }

        logger.info("Tweet de-serialization completed");
        logger.info("Total {} message(s) de-serialized", tweets.size());

        return tweets;
    }

    public void concludeProcessing() {
        keepRunning = false;
    }
}
