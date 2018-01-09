package com.techpearl.tvguide;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.techpearl.tvguide.databinding.ActivityMainBinding;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mBinding;
    private static final String PARAM_DATE = "date";
    private static final String PARAM_COUNTRY = "country";
    private static final String SCHEDULE_PATH = "schedule";
    private static final String TV_MAZE_BASE_URL = "http://api.tvmaze.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.responseTextView.setText(buildURL().toString());
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
    private URL buildURL(){

        String date = "2018-01-09";
        String countryCode = "GB";
        Uri builtUri = Uri.parse(TV_MAZE_BASE_URL).buildUpon()
                .appendPath(SCHEDULE_PATH)
                .appendQueryParameter(PARAM_COUNTRY, countryCode)
                .appendQueryParameter(PARAM_DATE, date)
                .build();
        URL scheduleURL = null;
        try {
            scheduleURL = new URL(builtUri.toString());
        }catch (MalformedURLException exp){
            exp.printStackTrace();
        }
        return scheduleURL;
    }
}

