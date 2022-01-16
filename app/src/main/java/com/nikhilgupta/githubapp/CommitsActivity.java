package com.nikhilgupta.githubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.nikhilgupta.githubapp.adapters.CommitsRVAdapter;
import com.nikhilgupta.githubapp.pojo.Commit;
import com.nikhilgupta.githubapp.utilities.APIInterface;
import com.nikhilgupta.githubapp.utilities.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommitsActivity extends AppCompatActivity {
    private static final String TAG = CommitsActivity.class.getSimpleName();
    private String mUser,mRepo;
    RecyclerView mRecyclerView;
    CommitsRVAdapter mAdapter;
    APIInterface apiInterface;
    List<Commit> mCommitList; // To store the list of objects of Commits

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits);
        mRecyclerView = findViewById(R.id.recyclerViewCommits);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(IssuesActivity.EXTRA_USER_KEY) && intent.hasExtra(IssuesActivity.EXTRA_REPO_KEY)){
            mUser = intent.getStringExtra(IssuesActivity.EXTRA_USER_KEY);
            mRepo = intent.getStringExtra(IssuesActivity.EXTRA_REPO_KEY);
        }
        apiInterface = RetrofitClient.getInstance().create(APIInterface.class);
        Call<List<Commit>> commitCall = apiInterface.getCommits(mUser,mRepo);
        commitCall.enqueue(new Callback<List<Commit>>() {
            @Override
            public void onResponse(Call<List<Commit>> call, Response<List<Commit>> response) {
                if(response.isSuccessful()){
//                    repo = new Repo(response.body().get(0).name);
                    mCommitList = new ArrayList<>();
                    response.body().forEach((temp) -> {
                        mCommitList.add(new Commit(temp.sha,temp.innerCommit));
                        Log.d(TAG, "onResponse: "+temp.sha+" "+temp.innerCommit.message+", ");
                    });
                    Log.d(TAG, "initializing adapter");
                    mAdapter = new CommitsRVAdapter(mCommitList,CommitsActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    Log.d(TAG, "onResponse2: \n" + response);
                    Log.d(TAG, "onResponse3: code=" + response.code());
                    Log.d(TAG, "onResponse4: body=" + response.body());
                    Log.d(TAG, "onResponse5: errorBOdy=" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Commit>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}