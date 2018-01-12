package com.techpearl.tvguide;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techpearl.tvguide.databinding.EpisodeRowLayoutBinding;

/**
 * Created by Nour on 1/12/2018.
 */

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder>{
    private String[] data;
    private EpisodesAdapter.ListItemClickListener mListener;

    public EpisodesAdapter(ListItemClickListener listener){
        this.mListener = listener;
    }

    public void setData(String[] data){
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
        String episodeString = data[position];
        holder.bind(episodeString);
    }

    @Override
    public int getItemCount() {
        if(data == null)
            return 0;
        return data.length;
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
        public void bind(String episodeData){
            mBinder.nameTextView.setText(episodeData);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mListener.onItemClick(data[position]);
        }
    }
}
