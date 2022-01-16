package com.nikhilgupta.githubapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IssueRVAdapter extends RecyclerView.Adapter<IssueRVAdapter.IssueViewHolder>{
    private final List<Issue> mIssues;
    private final Context mContext;

    // Member variable to handle item clicks
    final private IssueRVAdapter.ItemClickListener mItemClickListener;

    public IssueRVAdapter(List<Issue> mIssues, Context mContext, ItemClickListener mItemClickListener) {
        this.mIssues = mIssues;
        this.mContext = mContext;
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public IssueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the repo layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.repo_view,parent,false);
        return new IssueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueViewHolder holder, int position) {
        // Determine the values of the wanted data
        Issue issue = mIssues.get(position);

        // set values
        holder.numberTV.setText("#"+issue.getNumber());
        holder.issueTitle.setText(issue.getTitle());
        holder.issueTV.setVisibility(View.GONE);
        holder.commitTV.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        if (mIssues == null) {
            return 0;
        }
        return mIssues.size();
    }

    public interface ItemClickListener {
        void onItemCLickListener(String number, String title, String state, String updatedAt, String body);
    }

    public class IssueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView numberTV,issueTitle, issueTV, commitTV;

        public IssueViewHolder(@NonNull View itemView) {
            super(itemView);
            numberTV = itemView.findViewById(R.id.user);
            issueTitle = itemView.findViewById(R.id.repo);
            issueTV = itemView.findViewById(R.id.issue);
            commitTV = itemView.findViewById(R.id.commit);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String number = mIssues.get(getAdapterPosition()).getNumber();
            String title = mIssues.get(getAdapterPosition()).getTitle();
            String state = mIssues.get(getAdapterPosition()).getState();
            String updatedAt = mIssues.get(getAdapterPosition()).getUpdatedAt();
            String body = mIssues.get(getAdapterPosition()).getBody();
            mItemClickListener.onItemCLickListener(number, title, state, updatedAt,body);
        }
    }
}
