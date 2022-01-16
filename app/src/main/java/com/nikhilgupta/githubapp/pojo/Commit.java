package com.nikhilgupta.githubapp.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Class to Modularize the Commit objects
 */
public class Commit {
    @SerializedName("sha")
    public String sha;
    @SerializedName("commit")
    public InnerCommit innerCommit;

    public Commit(String sha, InnerCommit innerCommit) {
        this.sha = sha;
        this.innerCommit = innerCommit;
    }

    public static class InnerCommit {
        @SerializedName("message")
        public String message;

        public InnerCommit(String message) {
            this.message = message;
        }
    }
}
