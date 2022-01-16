package com.nikhilgupta.githubapp.pojo;

import com.google.gson.annotations.SerializedName;
/*
"node_id": "MDEwOlJlcG9zaXRvcnk0MDg1MTk4MDg=",
"name": "Bluej",
"full_name": "Nikhil-Gupta-ind/Bluej",
"private": false,
"owner": {
            "login": "Nikhil-Gupta-ind",
            "id": 85886260,
*/

/**
 * Class to Modularize the RepositoriesActivity objects
 */
public class Repo {

    @SerializedName("name")
    public String repoName;
    @SerializedName("owner")
    public Owner owner;

    public Repo(String repoName, Owner owner) {
        this.repoName = repoName;
        this.owner = owner;
    }

    public static class Owner {

        @SerializedName("login")
        public String user;

        public Owner(String user) {
            this.user = user;
        }

        public String getUser() {
            return user;
        }
    }
}
