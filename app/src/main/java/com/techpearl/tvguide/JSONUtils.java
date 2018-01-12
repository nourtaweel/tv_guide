package com.techpearl.tvguide;

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

    public static String[] parseScheduleResponse(String jsonResponse){
        String[] episodesArray = null;
        try{
            JSONArray reader = new JSONArray(jsonResponse);
            episodesArray = new String[reader.length()];
            for(int i = 0; i < reader.length() ; ++i){
                JSONObject episode = reader.getJSONObject(i);
                String episodeID = episode.getString(KEY_EPISODE_ID);
                String episodeName = episode.getString(KEY_EPISODE_NAME);
                String episodeSeason = episode.getString(KEY_EPISODE_SEASON);
                String episodeNumber = episode.getString(KEY_EPISODE_NUM);
                String episodeAirTime = episode.getString(KEY_EPISODE_AIR_TIME);
                String episodeRunTime = episode.getString(KEY_EPISODE_RUN_TIME);
                String episodeImage = episode.getString(KEY_EPISODE_IMAGE);
                String episodeSummary = episode.getString(KEY_EPISODE_SUMMARY);
                JSONObject episodeShow = episode.getJSONObject(KEY_EPISODE_SHOW);
                String episodeShowId = episodeShow.getString(KEY_EPISODE_SHOW_ID);
                String episodeShowName = episodeShow.getString(KEY_EPISODE_SHOW_NAME);
                String episodeShowGenres = episodeShow.getString(KEY_EPISODE_SHOW_GENRES);
                JSONObject episodeShowNetwork = episodeShow.getJSONObject(KEY_EPISODE_SHOW_NETWORK);
                String episodeShowNetworkId = episodeShowNetwork.getString(KEY_EPISODE_SHOW_NETWORK_ID);
                String episodeShowNetworkName = episodeShowNetwork.getString(KEY_EPISODE_SHOW_NETWORK_NAME);
                String episodeShowImage = episodeShow.getString(KEY_EPISODE_SHOW_IMAGE);
                episodesArray[i] = episodeShowName
                        + " " + episodeAirTime 
                        + " s" + episodeSeason
                        + " ep " + episodeNumber;
            }
        } catch (JSONException jsonExp){
            jsonExp.printStackTrace();
        }
        return episodesArray;
    }
}
