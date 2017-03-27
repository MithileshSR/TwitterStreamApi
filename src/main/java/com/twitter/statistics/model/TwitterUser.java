package com.twitter.statistics.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.twitter.statistics.utils.DateTimeUtil;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterUser {

    private static final String dateTimePattern = "EEE MMM dd HH:mm:ss Z yyyy";

    private long id;

    @JsonProperty("created_at")
    private String createdAt;

    private String name;

    @JsonProperty("screen_name")
    private String screenName;

    /**
     * Method to get the id
     *
     * @return id of the TwitterUser object
     */
    public long getId() {
        return this.id;
    }

    /**
     * Method to set the id of the TwitterUser object
     *
     * @param id of type long
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Method to convert the createdAt property in TwitterUser Object
     * to epoch time
     *
     * @return long value of the time
     */
    public long getCreatedAtEpoch() {
        return DateTimeUtil.toEpochTime(createdAt, dateTimePattern);
    }

    /**
     * Method to get the CreateAt field value from the Twitter Object
     *
     * @return String holds the value when the TwitterObject is created
     */
    public String getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Method to set the Created at field value in Twitter Object
     *
     * @param createdAt of type String
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Method to get the Name field of the TwitterUser object
     *
     * @return Sting holds the Name of the TwitterUser
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method to set the name field in the TwitterUser object
     *
     * @param name of type String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to get the ScreenName field of the TwitterUser object
     *
     * @return Sting holds the ScreenName of the TwitterUser
     */
    public String getScreenName() {
        return this.screenName;
    }

    /**
     * Method to set the ScreenName field in the TwitterUser object
     *
     * @param screenName of type String
     */
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }


    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }

    @Override
    public boolean equals(Object other) {

        if ((other == null) || !(other instanceof TwitterUser)) {
            return false;
        }

        TwitterUser otherTwitterUser = (TwitterUser) other;

        return id == otherTwitterUser.getId();
    }

    @Override
    public String toString() {

        StringBuilder output = new StringBuilder("TwitterUser Id: " + id + "\n");

        output.append("{").append("\n");
        output.append("Screen Name: ").append(screenName).append("\n");
        output.append("Name: ").append(name).append("\n");
        output.append("Created Time: ").append(getCreatedAtEpoch()).append("\n");
        output.append('}').append("\n");

        return output.toString();
    }
}
