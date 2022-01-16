package com.nikhilgupta.githubapp.utilities;

import com.nikhilgupta.githubapp.Commit;
import com.nikhilgupta.githubapp.GitEvent;
import com.nikhilgupta.githubapp.Issue;
import com.nikhilgupta.githubapp.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Interface class- This Java class is used to define endpoints.
 */
public interface APIInterface {
//    https://api.github.com/users/octocat/events
//    https://api.github.com/users/octocat/repos
//    https://api.github.com/repos/octocat/hello-world/issues
//    https://api.github.com/repos/octocat/hello-world/commits

    @GET("/users/{user}")
    Call<RawResult> getResult(@Path("user") String user);

    // To get the Events (recent activity)
    @GET("/users/{user}/events")
    Call<List<GitEvent>> getEvents(@Path("user") String user);

    // To get the repos
    @GET("/users/{user}/repos")
    Call<List<Repo>> getRepos(@Path("user") String user);

    // To get the issues
    @GET("/repos/{user}/{repo}/issues")
    Call<List<Issue>> getIssues(@Path("user") String user, @Path("repo") String repoName);

    // To get the commits
    @GET("/repos/{user}/{repo}/commits")
    Call<List<Commit>> getCommits(@Path("user") String user, @Path("repo") String repoName);
}
