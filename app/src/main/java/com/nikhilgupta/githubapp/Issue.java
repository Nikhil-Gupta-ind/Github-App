package com.nikhilgupta.githubapp;

import com.google.gson.annotations.SerializedName;

/**
 * Class to Modularize the Issues objects
 */
public class Issue {
    @SerializedName("number")
    String number;
    @SerializedName("title")
    String title;
    @SerializedName("state")
    String state;
    @SerializedName("updated_at")
    String updatedAt;
    @SerializedName("body")
    String body;

    public Issue(String number, String title, String state, String updatedAt, String body) {
        this.number = number;
        this.title = title;
        this.state = state;
        this.updatedAt = updatedAt;
        this.body = body;
    }

    public String getState() {
        return state;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getBody() {
        return body;
    }

    public String getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }
}
