package com.techpearl.tvguide;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.techpearl.tvguide.databinding.ActivityEpisodeDetailsBinding;
import com.techpearl.tvguide.databinding.EpisodeRowLayoutBinding;

public class EpisodeDetailsActivity extends AppCompatActivity {

    ActivityEpisodeDetailsBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_episode_details);
        Intent startingIntent = getIntent();
        if(startingIntent.hasExtra(Intent.EXTRA_TEXT)){
            mBinding.detailsTextView.setText(startingIntent.getStringExtra(Intent.EXTRA_TEXT));

        }

    }
}
