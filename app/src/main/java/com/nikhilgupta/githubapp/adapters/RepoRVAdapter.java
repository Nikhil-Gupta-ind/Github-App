package com.nikhilgupta.githubapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikhilgupta.githubapp.CommitsActivity;
import com.nikhilgupta.githubapp.IssuesActivity;
import com.nikhilgupta.githubapp.R;
import com.nikhilgupta.githubapp.pojo.Repo;

import java.util.List;

public class RepoRVAdapter extends RecyclerView.Adapter<RepoRVAdapter.RepoViewHolder> {
    private static final String TAG = RepoRVAdapter.class.getSimpleName();
    private final List<Repo> mRepos;
    private final Context mContext;

    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;

    public RepoRVAdapter(Context context, List<Repo> mRepos, ItemClickListener mItemClickListener) {
        this.mRepos = mRepos;
        this.mContext = context;
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the repo layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.repo_view,parent,false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        // Determine the values of the wanted data
        Repo repo = mRepos.get(position);

        // set values
        holder.userTV.setText(repo.owner.user);
        holder.repoTV.setText(repo.repoName);
    }

    @Override
    public int getItemCount() {
        if (mRepos == null) {
            return 0;
        }
        return mRepos.size();
    }

    public interface ItemClickListener {
        void onItemCLickListener(String user, String repoName);
    }

    public class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView userTV,repoTV, issueTV, commitTV;

        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            userTV = itemView.findViewById(R.id.user);
            repoTV = itemView.findViewById(R.id.repo);
            issueTV = itemView.findViewById(R.id.issue);
            commitTV = itemView.findViewById(R.id.commit);

            itemView.setOnClickListener(this);
            issueTV.setOnClickListener(v -> {
                Log.d(TAG, "onClick: ");
                String user = mRepos.get(getAdapterPosition()).owner.user;
                String repoName = mRepos.get(getAdapterPosition()).repoName;
                Intent intent = new Intent(mContext, IssuesActivity.class);
                intent.putExtra(IssuesActivity.EXTRA_USER_KEY, user);
                intent.putExtra(IssuesActivity.EXTRA_REPO_KEY, repoName);
                mContext.startActivity(intent);
            });
            commitTV.setOnClickListener(v -> {
                Log.d(TAG, "onClick in onBind: ");
                String user = mRepos.get(getAdapterPosition()).owner.user;
                String repoName = mRepos.get(getAdapterPosition()).repoName;
                Intent intent = new Intent(mContext, CommitsActivity.class);
                intent.putExtra(IssuesActivity.EXTRA_USER_KEY, user);
                intent.putExtra(IssuesActivity.EXTRA_REPO_KEY, repoName);
                mContext.startActivity(intent);
            });
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick in ViewHolder: ");
            String user = mRepos.get(getAdapterPosition()).owner.user;
            String repoName = mRepos.get(getAdapterPosition()).repoName;
            mItemClickListener.onItemCLickListener(user,repoName);
        }
    }
}
