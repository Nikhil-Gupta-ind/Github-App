package com.nikhilgupta.githubapp.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    final static String BASE_URL = "https://api.github.com";
//    https://api.github.com/users/octocat/repos
//    https://api.github.com/repos/octocat/hello-world/issues
//    https://api.github.com/repos/octocat/hello-world/commits
    public static Retrofit getInstance() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
