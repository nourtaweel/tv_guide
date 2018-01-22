package com.techpearl.tvguide;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.techpearl.tvguide.databinding.ActivityMainBinding;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements EpisodesAdapter.ListItemClickListener
,LoaderManager.LoaderCallbacks<String>,SharedPreferences.OnSharedPreferenceChangeListener{
    ActivityMainBinding mBinding;
    String[] dataArray;
    EpisodesAdapter mAdapter;
    private static final int SCHEDULE_LOADER_ID = 100;
    private static final String DATE_LOADER_EXTRA = "date";
    private static final String COUNTRY_CODE_LOADER_EXTRA = "country";
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mAdapter = new EpisodesAdapter(this);
        mBinding.episodesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.episodesRecyclerView.setAdapter(mAdapter);
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
        makeConnectionToApi();
    }
    private void makeConnectionToApi() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String countryCodePreferenceKey = getResources().getString(R.string.pref_country_code_key);
        String defaultCountryCode = getResources().getString(R.string.pref_country_code_default);
        String countryCode = sharedPreferences.getString(countryCodePreferenceKey, defaultCountryCode);
        Log.d(TAG, countryCode);
        String date = "2018-01-19";

        Bundle b = new Bundle();
        b.putString(DATE_LOADER_EXTRA, date);
        b.putString(COUNTRY_CODE_LOADER_EXTRA, countryCode);
        getLoaderManager().initLoader(SCHEDULE_LOADER_ID, b, MainActivity.this);
       // getLoaderManager().getLoader(SCHEDULE_LOADER_ID).forceLoad();
    }
    private void showResponse(String response){
        mBinding.errorTextView.setVisibility(View.INVISIBLE);
        mBinding.episodesRecyclerView.setVisibility(View.VISIBLE);
        dataArray = JSONUtils.parseScheduleResponse(response);
        mAdapter.setData(dataArray);
    }
    private void showErrorMessage(){
        mBinding.episodesRecyclerView.setVisibility(View.INVISIBLE);
        mBinding.errorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(String itemData) {
        openDetails(itemData);
    }

    private void openDetails(String itemData) {
        Intent detailsIntent = new Intent(MainActivity.this, EpisodeDetailsActivity.class);
        detailsIntent.putExtra(Intent.EXTRA_TEXT, itemData);
        startActivity(detailsIntent);
    }


    @Override
    public Loader<String> onCreateLoader(int i, final Bundle bundle) {
        //TODO : check what to do if bundle is null
        String date = bundle.getString(DATE_LOADER_EXTRA);
        String country = bundle.getString(COUNTRY_CODE_LOADER_EXTRA);
        URL tvMazeUrl = NetworkUtils.buildURL(date, country);
        return new ScheduleLoader(this, tvMazeUrl);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        Log.d(TAG, "onLoadFinished()");
        mBinding.progressBar.setVisibility(View.INVISIBLE);
        if( s == null || s.isEmpty() )
            showErrorMessage();
        else
            showResponse(s);
    }


    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String countryCodeKey = getResources().getString(R.string.pref_country_code_key);
        if(key.equals(countryCodeKey)){
            String newCountryCode = sharedPreferences.getString(countryCodeKey,
                    getResources().getString(R.string.pref_country_code_default));
            Bundle b = new Bundle();
            b.putString(DATE_LOADER_EXTRA, "2018-01-19");
            b.putString(COUNTRY_CODE_LOADER_EXTRA, newCountryCode);
            getLoaderManager().restartLoader(SCHEDULE_LOADER_ID, b, MainActivity.this);
        }
    }

    private static class ScheduleLoader extends AsyncTaskLoader<String> {
        private String mData;
        private URL mUrl;
        ScheduleLoader(Context context, URL url) {
            super(context);
            mUrl = url;
        }

        @Override
        public String loadInBackground() {
            Log.d(TAG, "loadInBackground()");
            String response = null;
            try{
                response = NetworkUtils.makeConnection(mUrl);}
            catch (IOException ioe){
                ioe.printStackTrace();
            }
            return response;
        }

        @Override
        public void deliverResult(String data) {
            Log.d(TAG, "deliverResult()");
            mData = data;
            super.deliverResult(data);
        }

        @Override
        protected void onStartLoading() {
            if(mData !=  null){
                Log.d(TAG, "onStartLoading() and have cached data -> deliver");
                deliverResult(mData);}
            else{
                Log.d(TAG, "onStartLoading() and no cached data -> forceLoad()");
                forceLoad();}
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}

