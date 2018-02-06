package com.techpearl.tvguide.sync;

import android.content.ContentValues;
import android.content.Context;

import com.techpearl.tvguide.JSONUtils;
import com.techpearl.tvguide.NetworkUtils;
import com.techpearl.tvguide.data.ScheduleContract;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Nour on 2/5/2018.
 */

public class TvGuideSyncTask {
    public static void syncGuide(Context context, URL url){
        //performing network connection
        String response = null;
        try{
            response = NetworkUtils.makeConnection(url);}
        catch (IOException ioe){
            ioe.printStackTrace();
        }
        if(response == null || response.isEmpty())
            return;
        //parsing response to ContentValues array
        ContentValues[] contentValues = JSONUtils.parseScheduleResponse(response);
        //deleting all old data
        context.getContentResolver().delete(ScheduleContract.ScheduleEntry.CONTENT_URI,
                null,
                null);
        //bulk insert new data
        context.getContentResolver().bulkInsert(ScheduleContract.ScheduleEntry.CONTENT_URI,
                contentValues);
        //mAdapter.setData(dataArray);
    }
}
