package com.techpearl.tvguide;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.techpearl.tvguide.data.ScheduleContract;
import com.techpearl.tvguide.databinding.ActivityMainBinding;
import com.techpearl.tvguide.sync.TvGuideSyncUtils;

public class MainActivity extends AppCompatActivity implements EpisodesAdapter.ListItemClickListener
,LoaderManager.LoaderCallbacks<Cursor>{
    ActivityMainBinding mBinding;
    EpisodesAdapter mAdapter;
    private static final int SCHEDULE_LOADER_ID = 100;
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mAdapter = new EpisodesAdapter(this);
        mBinding.episodesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.episodesRecyclerView.setAdapter(mAdapter);
        TvGuideSyncUtils.startImmediateSync(this);
        showLoadingIndicator(true);
        getSupportLoaderManager().initLoader(SCHEDULE_LOADER_ID, null, this);
    }
    private void showResponse(Cursor response){
        mBinding.errorTextView.setVisibility(View.INVISIBLE);
        mBinding.episodesRecyclerView.setVisibility(View.VISIBLE);
        mAdapter.swapCursor(response);
    }
    private void showErrorMessage(){
        mBinding.episodesRecyclerView.setVisibility(View.INVISIBLE);
        mBinding.errorTextView.setVisibility(View.VISIBLE);
    }
    private void showLoadingIndicator(boolean show){
        mBinding.progressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onItemClick(String itemId) {
        openDetails(itemId);
    }

    private void openDetails(String itemData) {
        Intent detailsIntent = new Intent(MainActivity.this, EpisodeDetailsActivity.class);
        detailsIntent.putExtra(Intent.EXTRA_TEXT, itemData);
        startActivity(detailsIntent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, final Bundle bundle) {
        String[] projection = new String[]{ScheduleContract.ScheduleEntry._ID,
                ScheduleContract.ScheduleEntry.COLUMN_EP_ID,
                ScheduleContract.ScheduleEntry.COLUMN_NAME,
                ScheduleContract.ScheduleEntry.COLUMN_SEASON,
                ScheduleContract.ScheduleEntry.COLUMN_NUMBER,
                ScheduleContract.ScheduleEntry.COLUMN_AIR_TIME,
                ScheduleContract.ScheduleEntry.COLUMN_RUN_TIME,
                ScheduleContract.ScheduleEntry.COLUMN_IMAGE};
        return new CursorLoader(this,
                ScheduleContract.ScheduleEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        showLoadingIndicator(false);
        if( cursor == null || cursor.getCount() == 0 )
            showErrorMessage();
        else
            showResponse(cursor);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings){
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }
}

