package com.techpearl.tvguide;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Nour on 1/10/2018.
 */

public class NetworkUtils {

    private static final String PARAM_DATE = "date";
    private static final String PARAM_COUNTRY = "country";
    private static final String SCHEDULE_PATH = "schedule";
    private static final String TV_MAZE_BASE_URL = "http://api.tvmaze.com";

    public static URL buildURL(String date, String countryCode){

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
    public static String makeConnection(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream is = urlConnection.getInputStream();
            Scanner scanner = new Scanner(is);
            scanner.useDelimiter("//A");
            boolean hasNext = scanner.hasNext();
            if (hasNext) {
                return scanner.next();
            } else {
                return null;
            }
        }
        finally {
            urlConnection.disconnect();
        }
    }
}
