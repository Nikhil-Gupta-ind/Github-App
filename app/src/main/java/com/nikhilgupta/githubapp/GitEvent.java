package com.nikhilgupta.githubapp;

import com.google.gson.annotations.SerializedName;

public class GitEvent {
    @SerializedName("type")
    String type;
    @SerializedName("repo")
    ERepo repo;

    public GitEvent(String type, ERepo repo, String date) {
        this.type = type;
        this.repo = repo;
        this.date = date;
    }

    public class ERepo {
        @SerializedName("name")
        String name;

        public String getName() {
            return name;
        }
    }

    @SerializedName("created_at")
    String date;

    public String getType() {
        return type;
    }

    public ERepo getRepo() {
        return repo;
    }

    public String getDate() {
        return date;
    }
}
