package com.techpearl.tvguide;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import com.techpearl.tvguide.data.ScheduleContract;
import com.techpearl.tvguide.databinding.ActivityEpisodeDetailsBinding;
import com.techpearl.tvguide.databinding.EpisodeRowLayoutBinding;

public class EpisodeDetailsActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{
    private static final int EPISODE_INFO_LOADER = 101;

    ActivityEpisodeDetailsBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_episode_details);
        Intent startingIntent = getIntent();
        if(startingIntent.hasExtra(Intent.EXTRA_TEXT)){
            Log.d("DetailActivity", "_id= " + startingIntent.getStringExtra(Intent.EXTRA_TEXT));
            Bundle b = new Bundle();
            b.putString("_id", startingIntent.getStringExtra(Intent.EXTRA_TEXT));
           getSupportLoaderManager().initLoader(EPISODE_INFO_LOADER, b, this);
        }else{
            showError();
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String id = bundle.getString("_id");
        Log.d("in loader", id);
        String[] projection = new String[]{
                ScheduleContract.ScheduleEntry.COLUMN_SHOW_NAME,
                ScheduleContract.ScheduleEntry.COLUMN_AIR_TIME,
                ScheduleContract.ScheduleEntry.COLUMN_NETWORK_NAME,
                ScheduleContract.ScheduleEntry.COLUMN_SUMMARY,
                ScheduleContract.ScheduleEntry.COLUMN_IMAGE};
        return new CursorLoader(this,
                ScheduleContract.ScheduleEntry.getUriWithId(id),
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d("DetailActivity", "count= " + cursor.getCount());
        if(cursor == null)
            showError();
        else
            showResponse(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    private void showError() {
        mBinding.summarTextView.setText("something wrong");
    }

    private void showResponse(Cursor cursor) {
        cursor.moveToFirst();
        mBinding.nameTextView.setText(cursor.getString(cursor.getColumnIndex(
                ScheduleContract.ScheduleEntry.COLUMN_SHOW_NAME)));
        mBinding.networkTextView.setText("\u23F5" + cursor.getString(cursor.getColumnIndex(
                ScheduleContract.ScheduleEntry.COLUMN_NETWORK_NAME)));
        mBinding.timeTextView.setText("\u23F0" + cursor.getString(cursor.getColumnIndex(
                ScheduleContract.ScheduleEntry.COLUMN_AIR_TIME)));
        mBinding.summarTextView.setText(Html.fromHtml(cursor.getString(cursor.getColumnIndex(
                ScheduleContract.ScheduleEntry.COLUMN_SUMMARY))));
        GlideApp.with(mBinding.getRoot())
                .load(cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleEntry.COLUMN_IMAGE)))
                .into(mBinding.imageView);
    }
}
