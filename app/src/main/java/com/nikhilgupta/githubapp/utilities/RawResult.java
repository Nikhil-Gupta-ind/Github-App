package com.nikhilgupta.githubapp.utilities;

import com.google.gson.annotations.SerializedName;
// Handles raw json data of the user such as name, login, etc...
public class RawResult {
    public String rawJSON;

    @SerializedName("login")
    public String login;
    @SerializedName("avatar_url")
    public String avatarUrl;
    @SerializedName("name")
    public String name;
    @SerializedName("bio")
    public String bio;
}
