package com.techpearl.tvguide;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.techpearl.tvguide.databinding.ActivityMainBinding;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements EpisodesAdapter.ListItemClickListener{
    ActivityMainBinding mBinding;
    URL mURL;
    String[] dataArray;
    EpisodesAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mAdapter = new EpisodesAdapter(this);
        mBinding.episodesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.episodesRecyclerView.setAdapter(mAdapter);
        String date = "2018-01-09";
        String countryCode = "GB";
        mURL = NetworkUtils.buildURL(date, countryCode);
        makeConnectionToApi();
    }
    private void makeConnectionToApi() {
        new TVMazeAsyncTask().execute(mURL);

    }
    private void showResponse(String response){
        mBinding.errorTextView.setVisibility(View.INVISIBLE);
       // mBinding.responseTextView.setVisibility(View.VISIBLE);
        dataArray = JSONUtils.parseScheduleResponse(response);
        mAdapter.setData(dataArray);
        //for(String episode : responseParsedArray){
            // mBinding.responseTextView.append(episode + "\n\n");
        //}
       // mBinding.responseTextView.setText(response);
    }
    private void showErrorMessage(){
       // mBinding.responseTextView.setVisibility(View.INVISIBLE);
        mBinding.errorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(String itemData) {
        Toast.makeText(this,itemData,Toast.LENGTH_SHORT).show();
    }

    private class TVMazeAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            mBinding.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL tvMazeUrl = urls[0];
            String response = null;
            try{
               response = NetworkUtils.makeConnection(tvMazeUrl);}
            catch (IOException ioe){
                ioe.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            mBinding.progressBar.setVisibility(View.INVISIBLE);
            if( s == null || s.isEmpty() )
                showErrorMessage();
            else
            showResponse(s);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_search){
            Toast.makeText(this, "test menu", Toast.LENGTH_SHORT).show();
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }

}

