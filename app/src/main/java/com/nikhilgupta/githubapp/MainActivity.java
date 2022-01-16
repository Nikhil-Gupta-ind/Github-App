package com.nikhilgupta.githubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nikhilgupta.githubapp.adapters.EventsRVAdapter;
import com.nikhilgupta.githubapp.pojo.GitEvent;
import com.nikhilgupta.githubapp.utilities.APIInterface;
import com.nikhilgupta.githubapp.utilities.RawResult;
import com.nikhilgupta.githubapp.utilities.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText mSearchBoxEditText;
    private TextView mUrlDisplayTextView;
    private ImageView mAvatarImageView;
    private TextView mNameTextView;
    private TextView mUserTextView;
    private TextView mUserBio;
    private TextView mErrorMessageDisplay;
    private CardView mCardView;
    private TextView mEventsTextView;
    private ProgressBar mLoadingIndicator;
    String mUser = null;
    private RecyclerView mRecyclerView;
    private EventsRVAdapter mAdapter;
    List<GitEvent> mEventList; // To store the list of objects of Events
    APIInterface apiInterface;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing views
        mSearchBoxEditText = findViewById(R.id.et_search_box);
        mUrlDisplayTextView = findViewById(R.id.tv_url);
        mAvatarImageView = findViewById(R.id.iv_avatar);
        mNameTextView = findViewById(R.id.tv_name);
        mUserTextView = findViewById(R.id.tv_user);
        mUserBio = findViewById(R.id.tv_bio);
        mCardView = findViewById(R.id.cardView);
        mEventsTextView = findViewById(R.id.tv_history);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        Log.d("SharedTest", "onCreate: ");
        mRecyclerView = findViewById(R.id.rv_events);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Checking last username
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        mUser = sharedPreferences.getString("user", null);
        if (mUser != null) {
            mSearchBoxEditText.setText(mUser);
            makeSearch(findViewById(R.id.button));
        }
    }

    public void makeSearch(View view) {
        mUser = mSearchBoxEditText.getText().toString().trim();
        if (!mUser.equals("")){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user",mUser);
            editor.apply();
        }

        mLoadingIndicator.setVisibility(View.VISIBLE);
        apiInterface = RetrofitClient.getInstance().create(APIInterface.class);
        Call<RawResult> rawResultCall = apiInterface.getResult(mUser);
        rawResultCall.enqueue(new Callback<RawResult>() {
            @Override
            public void onResponse(@NonNull Call<RawResult> call, @NonNull Response<RawResult> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: "+response);
                    showJsonDataView();
                    Glide.with(MainActivity.this)
                            .load(response.body().avatarUrl)
//                            .circleCrop()
                            .into(mAvatarImageView);
                    mNameTextView.setText(response.body().name);
                    mUserTextView.setText(response.body().login);
                    mUserBio.setText(response.body().bio);
//                    mSearchBoxEditText.setText(response.body().rawJSON); how to print the raw json with retrofit
                } else {
                    Log.d(TAG, "onResponse2: \n" + response);
                    Log.d(TAG, "onResponse3: code=" + response.code());
                    Log.d(TAG, "onResponse4: body=" + response.body());
                    Log.d(TAG, "onResponse5: errorBOdy=" + response.errorBody());
                    showErrorMessage();
                }
                String url = response.toString().substring(response.toString().indexOf("url")+4,response.toString().length()-1);
                mUrlDisplayTextView.setText(url);
//                mLoadingIndicator.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<RawResult> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
        showEvents();
    }

    private void showEvents() {
        Call<List<GitEvent>> eventCall = apiInterface.getEvents(mUser);
        eventCall.enqueue(new Callback<List<GitEvent>>() {
            @Override
            public void onResponse(@NonNull Call<List<GitEvent>> call, @NonNull Response<List<GitEvent>> response) {
                if(response.isSuccessful()){
//                    repo = new Repo(response.body().get(0).name);
                    mEventList = new ArrayList<>();
                    response.body().forEach((temp) -> {
                        mEventList.add(new GitEvent(temp.type,temp.repo,temp.date));
                        Log.d(TAG, "onResponse: "+temp.repo.name+"\t"+temp.date+", "+temp.type);
                    });
                    Log.d(TAG, "initializing adapter");
                    mAdapter = new EventsRVAdapter(MainActivity.this,mEventList);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    Log.d(TAG, "onResponse2: \n" + response);
                    Log.d(TAG, "onResponse3: code=" + response.code());
                    Log.d(TAG, "onResponse4: body=" + response.body());
                    Log.d(TAG, "onResponse5: errorBOdy=" + response.errorBody());
                }
                mLoadingIndicator.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<List<GitEvent>> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void showErrorMessage() {
        // First, hide the currently visible data
        mAvatarImageView.setVisibility(View.INVISIBLE);
        mNameTextView.setVisibility(View.INVISIBLE);
        mUserTextView.setVisibility(View.INVISIBLE);
        mUserBio.setVisibility(View.GONE);
        mCardView.setVisibility(View.INVISIBLE);
        mEventsTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        // Then, show the error
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
    private void showJsonDataView() {
        // First, make sure the error is invisible
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        // Then, make sure the data is visible
        mAvatarImageView.setVisibility(View.VISIBLE);
        mNameTextView.setVisibility(View.VISIBLE);
        mUserTextView.setVisibility(View.VISIBLE);
        mUserBio.setVisibility(View.VISIBLE);
        mCardView.setVisibility(View.VISIBLE);
        mEventsTextView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public void openRepos(View view) {
        Intent intent = new Intent(this, RepositoriesActivity.class);
        if (mUser != null){
            intent.putExtra(RepositoriesActivity.EXTRA_USER_KEY,mUser);
        }
        startActivity(intent);
    }
}