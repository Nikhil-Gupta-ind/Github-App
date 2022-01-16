package com.nikhilgupta.githubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.nikhilgupta.githubapp.adapters.IssueRVAdapter;
import com.nikhilgupta.githubapp.pojo.Issue;
import com.nikhilgupta.githubapp.utilities.APIInterface;
import com.nikhilgupta.githubapp.utilities.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IssuesActivity extends AppCompatActivity implements IssueRVAdapter.ItemClickListener {

    public static final String EXTRA_USER_KEY = "extraUserKey";
    public static final String EXTRA_REPO_KEY = "extraRepoKey";
    private static final String TAG = IssuesActivity.class.getSimpleName();
    private String mUser,mRepo;
    RecyclerView mRecyclerView;
    IssueRVAdapter mAdapter;
    APIInterface apiInterface;
    List<Issue> mIssueList; // To store the list of objects of Issues

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        mRecyclerView = findViewById(R.id.recyclerViewIssues);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EXTRA_USER_KEY) && intent.hasExtra(EXTRA_REPO_KEY)){
            mUser = intent.getStringExtra(EXTRA_USER_KEY);
            mRepo = intent.getStringExtra(EXTRA_REPO_KEY);
        }
        apiInterface = RetrofitClient.getInstance().create(APIInterface.class);
        Call<List<Issue>> issueCall = apiInterface.getIssues(mUser,mRepo);
        issueCall.enqueue(new Callback<List<Issue>>() {
            @Override
            public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {
                if(response.isSuccessful()){
//                    repo = new Repo(response.body().get(0).name);
                    mIssueList = new ArrayList<>();
                    response.body().forEach((temp) -> {
                        mIssueList.add(new Issue(temp.number,temp.title, temp.state, temp.updatedAt, temp.body));
                        Log.d(TAG, "onResponse: "+temp.title+", ");
                    });
                    Log.d(TAG, "initializing adapter");
                    mAdapter = new IssueRVAdapter(mIssueList,IssuesActivity.this, IssuesActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    Log.d(TAG, "onResponse2: \n" + response);
                    Log.d(TAG, "onResponse3: code=" + response.code());
                    Log.d(TAG, "onResponse4: body=" + response.body());
                    Log.d(TAG, "onResponse5: errorBOdy=" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Issue>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onItemCLickListener(String number, String title, String state, String updatedAt, String body) {
        // Launch Issues Detail Activity adding the extras in the intent
        Intent intent = new Intent(this, IssueDetailActivity.class);
        intent.putExtra(IssueDetailActivity.EXTRA_NUMBER_KEY, number);
        intent.putExtra(IssueDetailActivity.EXTRA_TITLE_KEY, title);
        intent.putExtra(IssueDetailActivity.EXTRA_STATE_KEY, state);
        intent.putExtra(IssueDetailActivity.EXTRA_UPDATED_KEY, updatedAt);
        intent.putExtra(IssueDetailActivity.EXTRA_BODY_KEY, body);
        startActivity(intent);
    }
}