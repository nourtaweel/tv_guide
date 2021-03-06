package com.techpearl.tvguide;

import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techpearl.tvguide.data.ScheduleContract;
import com.techpearl.tvguide.databinding.EpisodeRowLayoutBinding;

/**
 * Created by Nour on 1/12/2018.
 */

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder>{
    private Cursor data;
    private EpisodesAdapter.ListItemClickListener mListener;

    public EpisodesAdapter(ListItemClickListener listener){
        this.mListener = listener;
    }

    public void swapCursor(Cursor data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public EpisodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        EpisodeRowLayoutBinding binder = EpisodeRowLayoutBinding.inflate(inflater,
                parent,
                false);
        return new EpisodeViewHolder(binder);
    }

    @Override
    public void onBindViewHolder(EpisodeViewHolder holder, int position) {
        data.moveToPosition(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        if(data == null)
            return 0;
        return data.getCount();
    }
    public interface ListItemClickListener{
        public void onItemClick(String itemData);
    }
    class EpisodeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        EpisodeRowLayoutBinding mBinder;
        public EpisodeViewHolder(EpisodeRowLayoutBinding binder) {
            super(binder.getRoot());
            mBinder = binder;
            binder.getRoot().setOnClickListener(this);
        }
        public void bind(Cursor episodeData){
            String name = data.getString(data.getColumnIndex(ScheduleContract.ScheduleEntry.COLUMN_SHOW_NAME));
            String networkName = data.getString(data.getColumnIndex(ScheduleContract.ScheduleEntry.COLUMN_NETWORK_NAME));
            String season = data.getString(data.getColumnIndex(ScheduleContract.ScheduleEntry.COLUMN_SEASON));
            String number = data.getString(data.getColumnIndex(ScheduleContract.ScheduleEntry.COLUMN_NUMBER));
            String airTime = data.getString(data.getColumnIndex(ScheduleContract.ScheduleEntry.COLUMN_AIR_TIME));
            String runTime = data.getString(data.getColumnIndex(ScheduleContract.ScheduleEntry.COLUMN_RUN_TIME));
            String image = data.getString(data.getColumnIndex(ScheduleContract.ScheduleEntry.COLUMN_IMAGE));
            mBinder.nameTextView.setText(name);
            mBinder.networkTextView.setText("\u2022" + networkName);
            mBinder.timeTextView.setText(airTime);
            GlideApp.with(mBinder.getRoot())
                    .load(image)
                    .into(mBinder.imageView);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            data.moveToPosition(position);
            Log.d("Adapter", "_id= " +data.getString(data.getColumnIndex(ScheduleContract.ScheduleEntry._ID)));
            mListener.onItemClick(data.getString(data.getColumnIndex(ScheduleContract.ScheduleEntry._ID)));
        }
    }
}
