package com.nikhilgupta.githubapp;

import com.google.gson.annotations.SerializedName;

/**
 * Class to Modularize the Commit objects
 */
public class Commit {
    @SerializedName("sha")
    String sha;
    @SerializedName("commit")
    public InnerCommit innerCommit;

    public Commit(String sha, InnerCommit innerCommit) {
        this.sha = sha;
        this.innerCommit = innerCommit;
    }

    public String getSha() {
        return sha;
    }

    public InnerCommit getInnerCommit() {
        return innerCommit;
    }

    public static class InnerCommit {
        @SerializedName("message")
        String message;

        public InnerCommit(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
