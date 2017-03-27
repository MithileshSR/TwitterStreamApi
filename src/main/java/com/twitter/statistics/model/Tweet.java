package com.twitter.statistics.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.twitter.statistics.utils.DateTimeUtil;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet {

    private static final String dateTimePattern = "EEE MMM dd HH:mm:ss Z yyyy";

    @JsonProperty("id_str")
    private String id;

    private String text;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("user")
    private TwitterUser twitterUser;

    /**
     * Method to get the id field of the Tweet object
     *
     * @return Sting holds the id of the Tweet
     */
    public String getId() {
        return this.id;
    }

    /**
     * Method to set the id field in the Tweet object
     *
     * @param id of type String
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Method to get the Text field of the Tweet object
     *
     * @return Sting holds the Text of the Tweet
     */
    public String getText() {
        return this.text;
    }

    /**
     * Method to set the Text field in the Tweet object
     *
     * @param text of type String
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Method to get the CreateAtEpoch field of the Tweet object which gives the string createdAt
     * field convert into epoch time
     *
     * @return Sting holds the createdAt time in long of the Tweet
     */
    public long getCreatedAtEpoch() {
        return DateTimeUtil.toEpochTime(createdAt, dateTimePattern);
    }

    /**
     * Method to get the CreatedAt field of the Tweet object
     *
     * @return Sting holds the CreatedAt of the Tweet
     */
    public String getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Method to set the CreatedAt field in the Tweet object
     *
     * @param createdAt of type String
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Method to get the TwitterUser Object of the Tweet object
     *
     * @return TwitterUser object of the Tweet
     */
    public TwitterUser getTwitterUser() {
        return this.twitterUser;
    }


    /**
     * Method to set the TwitterUser field in the Tweet object
     *
     * @param twitterUser of type TwitterUser
     */
    public void setTwitterUser(TwitterUser twitterUser) {
        this.twitterUser = twitterUser;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }

    @Override
    public boolean equals(Object other) {

        if ((other == null) || !(other instanceof Tweet)) {
            return false;
        }

        Tweet otherTweet = (Tweet) other;

        return id == otherTweet.getId();
    }

    @Override
    public String toString() {

        StringBuilder output = new StringBuilder("Tweet Id: " + id + "\n");

        output.append('{').append("\n");
        output.append("Text: ").append(text).append("\n");
        output.append("TwitterUser Id: ").append(twitterUser.getId()).append("\n");
        output.append("Created Time: ").append(getCreatedAtEpoch()).append("\n");
        output.append('}').append("\n");

        return output.toString();
    }
}
