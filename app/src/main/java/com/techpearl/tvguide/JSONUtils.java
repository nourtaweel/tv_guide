package com.techpearl.tvguide;

import android.content.ContentValues;
import android.util.Log;

import com.techpearl.tvguide.data.ScheduleContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nour on 1/10/2018.
 */

public class JSONUtils {
    private final static String KEY_EPISODE_ID = "id";
    private final static String KEY_EPISODE_NAME = "name";
    private final static String KEY_EPISODE_SEASON = "season";
    private final static String KEY_EPISODE_NUM = "number";
    private final static String KEY_EPISODE_AIR_TIME = "airtime";
    private final static String KEY_EPISODE_RUN_TIME = "runtime";
    private final static String KEY_EPISODE_IMAGE = "image";
    private final static String KEY_EPISODE_SUMMARY = "summary";
    private final static String KEY_EPISODE_SHOW = "show";
    private final static String KEY_EPISODE_SHOW_ID = "id";
    private final static String KEY_EPISODE_SHOW_NAME = "name";
    private final static String KEY_EPISODE_SHOW_GENRES = "genres";
    private final static String KEY_EPISODE_SHOW_NETWORK = "network";
    private final static String KEY_EPISODE_SHOW_NETWORK_ID = "id";
    private final static String KEY_EPISODE_SHOW_NETWORK_NAME = "name";
    private final static String KEY_EPISODE_SHOW_IMAGE = "image";
    private static final String KEY_EPISODE_SHOW_WEB_CHANNEL = "webChannel";
    private static final String KEY_EPISODE_SHOW_WEB_CHANNEL_NAME = "name";

    public static ContentValues[] parseScheduleResponse(String jsonResponse){
        ContentValues[] episodesContentValuesArray = null;
        try{
            JSONArray reader = new JSONArray(jsonResponse);
            episodesContentValuesArray = new ContentValues[reader.length()];
            for(int i = 0; i < reader.length() ; ++i){
                JSONObject episode = reader.getJSONObject(i);
                episodesContentValuesArray[i] = new ContentValues();
                episodesContentValuesArray[i].put(ScheduleContract.ScheduleEntry.COLUMN_EP_ID, episode.getString(KEY_EPISODE_ID));
                episodesContentValuesArray[i].put(ScheduleContract.ScheduleEntry.COLUMN_NAME, episode.getString(KEY_EPISODE_NAME));
                episodesContentValuesArray[i].put(ScheduleContract.ScheduleEntry.COLUMN_SEASON, episode.getString(KEY_EPISODE_SEASON));
                episodesContentValuesArray[i].put(ScheduleContract.ScheduleEntry.COLUMN_NUMBER, episode.getString(KEY_EPISODE_NUM));
                episodesContentValuesArray[i].put(ScheduleContract.ScheduleEntry.COLUMN_AIR_TIME, episode.getString(KEY_EPISODE_AIR_TIME));
                episodesContentValuesArray[i].put(ScheduleContract.ScheduleEntry.COLUMN_RUN_TIME, episode.getString(KEY_EPISODE_RUN_TIME));
                //episodesContentValuesArray[i].put(ScheduleContract.ScheduleEntry.COLUMN_IMAGE, episode.getString(KEY_EPISODE_IMAGE));
                episodesContentValuesArray[i].put(ScheduleContract.ScheduleEntry.COLUMN_SUMMARY, episode.getString(KEY_EPISODE_SUMMARY));
                JSONObject episodeShow = episode.getJSONObject(KEY_EPISODE_SHOW);
                String episodeShowName = episodeShow.getString(KEY_EPISODE_SHOW_NAME);
                episodesContentValuesArray[i].put(ScheduleContract.ScheduleEntry.COLUMN_SHOW_NAME, episodeShowName);
                //Tv show
                if(!episodeShow.getString(KEY_EPISODE_SHOW_NETWORK).equals("null")){
                    JSONObject episodeShowNetwork = episodeShow.getJSONObject(KEY_EPISODE_SHOW_NETWORK);
                    String episodeShowNetworkName = episodeShowNetwork.getString(KEY_EPISODE_SHOW_NETWORK_NAME);
                    episodesContentValuesArray[i].put(ScheduleContract.ScheduleEntry.COLUMN_NETWORK_NAME, episodeShowNetworkName);
                }//Web show
                else if(!episodeShow.getString(KEY_EPISODE_SHOW_WEB_CHANNEL).equals("null")){
                    JSONObject episodeShowWebChannel = episodeShow.getJSONObject(KEY_EPISODE_SHOW_WEB_CHANNEL);
                    String episodeShowWebChannelName = episodeShowWebChannel.getString(KEY_EPISODE_SHOW_WEB_CHANNEL_NAME);
                    episodesContentValuesArray[i].put(ScheduleContract.ScheduleEntry.COLUMN_NETWORK_NAME, episodeShowWebChannelName);
                }
                else{
                    episodesContentValuesArray[i].put(ScheduleContract.ScheduleEntry.COLUMN_NETWORK_NAME, "");
                }
                String episodeShowImage = episodeShow.getJSONObject(KEY_EPISODE_SHOW_IMAGE).getString("medium");
                episodesContentValuesArray[i].put(ScheduleContract.ScheduleEntry.COLUMN_IMAGE, episodeShowImage);

            }
        } catch (JSONException jsonExp){
            jsonExp.printStackTrace();
        }
        return episodesContentValuesArray;
    }
}
