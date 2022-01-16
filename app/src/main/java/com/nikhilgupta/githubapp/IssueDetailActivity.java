package com.nikhilgupta.githubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class IssueDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NUMBER_KEY = "extraNumberKey";
    public static final String EXTRA_TITLE_KEY = "extraTitleKey";
    public static final String EXTRA_STATE_KEY = "extraStateKey";
    public static final String EXTRA_UPDATED_KEY = "extraUpdateAtKey";
    public static final String EXTRA_BODY_KEY = "extraBodyKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_detail);
        TextView numberTV = findViewById(R.id.tv_number);
        TextView titleTV = findViewById(R.id.tv_title);
        TextView stateTV = findViewById(R.id.tv_state);
        TextView updateAtTV = findViewById(R.id.tv_updated_at);
        TextView bodyTV = findViewById(R.id.tv_body);

        Intent intent = getIntent();
        String number = "#"+intent.getStringExtra(EXTRA_NUMBER_KEY);
        String title = intent.getStringExtra(EXTRA_TITLE_KEY);
        String state = intent.getStringExtra(EXTRA_STATE_KEY).toUpperCase();
        String updateAt = intent.getStringExtra(EXTRA_UPDATED_KEY);
        String body = intent.getStringExtra(EXTRA_BODY_KEY);

        numberTV.setText(number);
        titleTV.setText(title);
        stateTV.setText(state);
        updateAtTV.setText(updateAt.substring(0,updateAt.indexOf('T')));
        bodyTV.setText(body);

        if (body == null){
            bodyTV.setVisibility(View.GONE);
        }
    }
}