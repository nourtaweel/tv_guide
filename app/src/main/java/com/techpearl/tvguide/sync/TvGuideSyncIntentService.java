package com.techpearl.tvguide.sync;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.techpearl.tvguide.NetworkUtils;
import com.techpearl.tvguide.R;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nour on 2/5/2018.
 */

public class TvGuideSyncIntentService extends IntentService {
    public TvGuideSyncIntentService() {
        super("TvGuideSyncIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String countryCodePreferenceKey = getResources().getString(R.string.pref_country_code_key);
        String defaultCountryCode = getResources().getString(R.string.pref_country_code_default);
        String countryCode = sharedPreferences.getString(countryCodePreferenceKey, defaultCountryCode);
        URL tvMazeUrl = NetworkUtils.buildURL(date, countryCode);
        TvGuideSyncTask.syncGuide(this, tvMazeUrl);
    }
}
