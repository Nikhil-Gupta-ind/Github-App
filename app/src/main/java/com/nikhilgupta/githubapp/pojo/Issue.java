package com.nikhilgupta.githubapp.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Class to Modularize the Issues objects
 */
public class Issue {
    @SerializedName("number")
    public String number;
    @SerializedName("title")
    public String title;
    @SerializedName("state")
    public String state;
    @SerializedName("updated_at")
    public String updatedAt;
    @SerializedName("body")
    public String body;

    public Issue(String number, String title, String state, String updatedAt, String body) {
        this.number = number;
        this.title = title;
        this.state = state;
        this.updatedAt = updatedAt;
        this.body = body;
    }
}
