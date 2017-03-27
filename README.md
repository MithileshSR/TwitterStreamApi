> *************************************************************
> *                                                           *
> *       ________  __    __  ________    ____       ______   *
> *      /_/_/_/_/ /_/   /_/ /_/_/_/_/  _/_/_/_   __/_/_/_/   *
> *     /_/_____  /_/___/_/    /_/    /_/___/_/  /_/          *
> *    /_/_/_/_/   /_/_/_/    /_/    /_/_/_/_/  /_/           *
> *   ______/_/       /_/    /_/    /_/   /_/  /_/____        *
> *  /_/_/_/_/       /_/    /_/    /_/   /_/    /_/_/_/ . io  *
> *                                                           *
> *************************************************************

This development test is used as part of Sytac selection for Java developers. You are requested to develop a simple TwitterStatistics that covers all the requirements listed below. To have an indication of the criteria that will be used to judge your submission, all the following are considered as metrics of good development:

+ Correctness of the implementation
+ Decent test coverage
+ Code cleanliness
+ Efficiency of the solution
+ Careful choice of tools and data formats
+ Use of production-ready approaches

While no specific time limit is mandated to complete the exercise, you will be asked to provide your code within a given deadline from Sytac HR. You are free to choose any library as long as it can run on the JVM.


## Task ##
We would like you to write code that will cover the functionality explained below and provide us with the source, instructions to build and run the appliocation  as well as a sample output of an execution:
+ Connect to the [Twitter Streaming API](https://dev.twitter.com/streaming/overview)
    * Use the following values:
        + Consumer Key: `RLSrphihyR4G2UxvA0XBkLAdl`
        + Consumer Secret: `FTz2KcP1y3pcLw0XXMX5Jy3GTobqUweITIFy4QefullmpPnKm4`
    * The app name will be `java-exercise`
    * You will need to login with Twitter
+ Filter messages that track on "bieber"
+ Retrieve the incoming messages for 30 seconds or up to 100 messages, whichever comes first
+ Your TwitterStatistics should return the messages grouped by user (users sorted chronologically, ascending)
+ The messages per user should also be sorted chronologically, ascending
+ For each message, we will need the following:
    * The message ID
    * The creation date of the message as epoch value
    * The text of the message
    * The author of the message
+ For each author, we will need the following:
    * The user ID
    * The creation date of the user as epoch value
    * The name of the user
    * The screen name of the user
+ All the above infomation is provided in either SDTOUT or a log file
+ You are free to choose the output format, provided that it makes it easy to parse and process by a machine

### __Bonus points for:__ ###

+ Keep track of messages per second statistics across multiple runs of the TwitterStatistics
+ The TwitterStatistics can run as a Docker container

## Provided functionality ##

The present project in itself is a [Maven project](http://maven.apache.org/) that contains one class that provides you with a `com.google.api.client.http.HttpRequestFactory` that is authorised to execute calls to the Twitter API in the scope of a specific user.
You will need to provide your _Consumer Key_ and _Consumer Secret_ and follow through the OAuth process (get temporary token, retrieve access URL, authorise TwitterStatistics, enter PIN for authenticated token).
With the resulting factory you are able to generate and execute all necessary requests.
If you want to, you can also disregard the provided classes or Maven configuration and create your own TwitterStatistics from scratch.


## Technologies/Libraries Used ##

1. Java (1.8)
2. Jackson (2.8.1) - To deserialize retrieved Json string into Java model
3. Logback (1.1.7) - To log tweets
4.Junit (4.11) - To Unit Testing the Code
5. Maven Shade Plugin (2.4.3) - To create fat executable jar

## Implementation Highlights ##

TwitterStatistics components are de-coupled. Message retrieving and processing happens in separate threads. It uses Java's ExecutorService to create and manage threads for TweetsRetriever and TweetsDeserializer. Please read below (core classes section) to see purpose of TweetsRetriever and TweetsDeserializer.

TweetsRetriever retrieves tweets and enqueues them into a queue as it retrieves. TweetsDeserializer monitors the queue and de-serialize the tweets as and when new tweet is available in the queue.

Implementation uses Java 8 lambda expressions wherever suitable.

TwitterStatistics handles non status tweets (e.g limit notices) sent by twitter and skips it's processing.

TwitterStatistics ensures resource cleaning and connection closing in all cases. It closes the streaming connection, shuts down thread pools before exiting.

The default logging level is set to "info" and it logs on console. Please update src/main/resources/logback.xml to turn on "debug" mode in case you want to see more finer details.

The TwitterStatistics is configurable by passing different JVM parameters while running TwitterStatistics. Please see usage for further details.

## Usage ##

The attachement contains an executable jar (twitter-statistics-1.0.0.jar) which can be run to execute the TwitterStatistics.

`java -jar twitter-statistics-1.0.0.jar`

Follow on-screen instructions to authenticate with twitter. Post successfull authentication, the TwitterStatistics will track 'bieber' for either 100 tweets or 30 seconds which ever occurs first.

Please pass following JVM arguements while running the TwitterStatistics to test TwitterStatistics.

-Dtracking.keywords=<Comma separated list of keywords to track commas as logical ORs, while spaces are equivalent to logical ANDs (e.g. ‘the twitter’ is the AND twitter, and ‘the,twitter’ is the OR twitter).>
-Dtracking.limit=<Maximum number of tweets to retrieve>
-Dtracking.timeoutseconds=<Tracking timeout in seconds>

For example, following execution will track "picnic" keyword to retrieve tweets for 20 seconds or 50 tweets, whichever occurs first

`java -Dtracking.keywords=earth -Dtracking.limit=50 -Dtracking.timeoutseconds=20 -jar twitter-statistics-1.0.0.jar`

## Core Classes ##

+ `TwitterStatisticsRunner`: TwitterStatisticsrunner to demo TwitterStatistics. It contains main() method.
+ `TwitterStatistics`: TwitterStatistics class to expose TwitterStatistics functionality. External TwitterStatistics/class should use this class's methods to authenticate, track and print tweets received from twitter. It contains following important methods.
        * authenticate() - Authenticates the TwitterStatistics with twitter
        * startTracking(stringToTrack, maxMessages, trackingTime) - Starts retrieving tweets from twitter.
        * printMessagesGroupedByAuthor() - Groups tweets by authors, sorts them chronologically and prints to console using TweetsPrinter

+ `TweetsRetriever`: Callable implementation to retrieve tweets from twitter and puts into a queue to be processes further by TweetsDeserializer
+ `TweetsDeserializer`: Callable implementation to de-serialize tweets retrieved from twitter into Java objects using Jackson
+ `TweetsProcessor`: Processes de-serialized twitter tweets to group tweets by twitterUser, sorts them chronologically

