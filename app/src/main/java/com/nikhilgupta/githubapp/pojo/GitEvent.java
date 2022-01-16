package com.nikhilgupta.githubapp.pojo;

import com.google.gson.annotations.SerializedName;

public class GitEvent {
    @SerializedName("type")
    public String type;
    @SerializedName("repo")
    public ERepo repo;

    public GitEvent(String type, ERepo repo, String date) {
        this.type = type;
        this.repo = repo;
        this.date = date;
    }

    public class ERepo {
        @SerializedName("name")
        public String name;

        public String getName() {
            return name;
        }
    }

    @SerializedName("created_at")
    public String date;
}
