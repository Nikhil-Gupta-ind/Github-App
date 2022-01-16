package com.nikhilgupta.githubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.nikhilgupta.githubapp.adapters.RepoRVAdapter;
import com.nikhilgupta.githubapp.pojo.Repo;
import com.nikhilgupta.githubapp.utilities.APIInterface;
import com.nikhilgupta.githubapp.utilities.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
// TODO 01 Make two icons for issues and for commits onclick opens respective activities
public class RepositoriesActivity extends AppCompatActivity implements RepoRVAdapter.ItemClickListener{

    // Extra for the task ID to be received in the intent
    public static final String EXTRA_USER_KEY = "extraUserKey";
    private static final String TAG = RepositoriesActivity.class.getSimpleName();
    private String mUser;
    RecyclerView mRecyclerView;
    RepoRVAdapter mAdapter;
    APIInterface apiInterface;
    List<Repo> mRepoList; // To store the list of objects of Repo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);
        mRecyclerView = findViewById(R.id.recyclerViewRepos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EXTRA_USER_KEY)){
            mUser = intent.getStringExtra(EXTRA_USER_KEY);
        }

        apiInterface = RetrofitClient.getInstance().create(APIInterface.class);
        Call<List<Repo>> repoCall = apiInterface.getRepos(mUser);
        repoCall.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                if(response.isSuccessful()){
//                    repo = new Repo(response.body().get(0).name);
                    mRepoList = new ArrayList<>();
                    response.body().forEach((temp) -> {
                        mRepoList.add(new Repo(temp.repoName,temp.owner));
                        Log.d(TAG, "onResponse: "+temp.owner.user+" "+temp.repoName+", ");
                    });
                    Log.d(TAG, "initializing adapter");
                    mAdapter = new RepoRVAdapter(RepositoriesActivity.this,mRepoList,RepositoriesActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    Log.d(TAG, "onResponse2: \n" + response);
                    Log.d(TAG, "onResponse3: code=" + response.code());
                    Log.d(TAG, "onResponse4: body=" + response.body());
                    Log.d(TAG, "onResponse5: errorBOdy=" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onItemCLickListener(String user, String repo) {
        Log.d(TAG, "onItemCLickListener: ");
        // Launch Issues Activity adding the user and clicked repo as an extra in the intent
        Intent intent = new Intent(this, IssuesActivity.class);
        intent.putExtra(IssuesActivity.EXTRA_USER_KEY, user);
        intent.putExtra(IssuesActivity.EXTRA_REPO_KEY, repo);
        startActivity(intent);
    }
}