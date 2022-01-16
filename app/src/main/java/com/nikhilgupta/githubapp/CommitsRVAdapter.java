package com.nikhilgupta.githubapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommitsRVAdapter extends RecyclerView.Adapter<CommitsRVAdapter.CommitViewHolder>{
    private final List<Commit> mCommits;
    private final Context mContext;

    public CommitsRVAdapter(List<Commit> mCommits, Context mContext) {
        this.mCommits = mCommits;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CommitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the repo layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.repo_view,parent,false);
        return new CommitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommitViewHolder holder, int position) {
        // Determine the values of the wanted data
        Commit commit = mCommits.get(position);

        // set values
        holder.shaTV.setText(commit.getSha());
        holder.messageTV.setText(commit.getInnerCommit().getMessage());
        holder.issueTV.setVisibility(View.GONE);
        holder.commitTV.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        if (mCommits == null) {
            return 0;
        }
        return mCommits.size();
    }

    public static class CommitViewHolder extends RecyclerView.ViewHolder {
        TextView shaTV,messageTV, issueTV, commitTV;

        public CommitViewHolder(@NonNull View itemView) {
            super(itemView);
            shaTV = itemView.findViewById(R.id.user);
            messageTV = itemView.findViewById(R.id.repo);
            issueTV = itemView.findViewById(R.id.issue);
            commitTV = itemView.findViewById(R.id.commit);
        }
    }
}
