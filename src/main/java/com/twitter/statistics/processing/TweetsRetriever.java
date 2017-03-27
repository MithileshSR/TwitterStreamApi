package com.twitter.statistics.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.*;

/**
 * A Callable implementation to retrieve messages from twitter stream. It
 * retrieves messages till trackingTimeout or maxMessages which ever occurs
 * first. It enqueues raw messages into messageQueue.
 * 
 * @author Mithilesh Ravindran
 *
 */
public class TweetsRetriever implements Callable<Boolean> {

    private static final Logger logger = LoggerFactory.getLogger(TweetsRetriever.class);

    private InputStream source;

    private BlockingQueue<String> messageQueue;

    private int maxMessages;

    private int trackingTimeout;

    private boolean keepReceiving = true;

    public TweetsRetriever(InputStream source, BlockingQueue<String> messageQueue, int maxMessages,
            int trackingTimeout) {
        this.source = source;
        this.messageQueue = messageQueue;
        this.maxMessages = maxMessages;
        this.trackingTimeout = trackingTimeout;
    }

    @Override
    public Boolean call() throws Exception {

        logger.info("Started retrieving messages");

        int messagesReceived = 0;
        ExecutorService stopTimer = setStopTimer();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(source));

            while ((messagesReceived != maxMessages) && keepReceiving) {

                String message = reader.readLine();
                messageQueue.offer(message);
                logger.debug("Retrieved message: {}", message);

                messagesReceived++;
            }

        } catch (Throwable t) {
            logger.error("Exception while retrieving messages", t);
        } finally {

            logger.info("Shutting down stop timer");
            stopTimer.shutdownNow();
        }

        logger.info("Tweet retrieving stopped");
        logger.info("Total {} message(s) retrieved", messagesReceived);

        return Boolean.TRUE;
    }

    private ExecutorService setStopTimer() {

        Runnable stopTimer = () -> {
            keepReceiving = false;
            logger.info("Stop timer expired. Halting message retriever!");
        };

        logger.info("Setting stop timer");
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutor.schedule(stopTimer, trackingTimeout, TimeUnit.SECONDS);

        return scheduledExecutor;
    }
}
