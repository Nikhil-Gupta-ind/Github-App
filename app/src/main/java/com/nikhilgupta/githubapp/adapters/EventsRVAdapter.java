package com.nikhilgupta.githubapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikhilgupta.githubapp.R;
import com.nikhilgupta.githubapp.pojo.GitEvent;

import java.util.List;

public class EventsRVAdapter extends RecyclerView.Adapter<EventsRVAdapter.EventViewHolder>{
    private static final String TAG = RepoRVAdapter.class.getSimpleName();
    private final Context mContext;
    private final List<GitEvent> mEvents;

    public EventsRVAdapter(Context mContext, List<GitEvent> mEvents) {
        this.mContext = mContext;
        this.mEvents = mEvents;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the repo layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.repo_view,parent,false);
        return new EventsRVAdapter.EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        // Determine the values of the wanted data
        GitEvent event = mEvents.get(position);

        // set values
        holder.nameTV.setText(event.repo.name);
        holder.typeTV.setText(event.type);
        holder.timeTV.setVisibility(View.GONE);
        String date = event.date;
        holder.dateTV.setText(date.substring(0,date.indexOf('T')));
    }

    @Override
    public int getItemCount() {
        if (mEvents == null) {
            return 0;
        }
        return mEvents.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder{
        TextView nameTV,typeTV, timeTV, dateTV;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.user);
            typeTV = itemView.findViewById(R.id.repo);
            timeTV = itemView.findViewById(R.id.issue);
            dateTV = itemView.findViewById(R.id.commit);
        }
    }
}
