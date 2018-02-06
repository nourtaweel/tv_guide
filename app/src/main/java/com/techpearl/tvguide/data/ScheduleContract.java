package com.techpearl.tvguide.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Nour on 1/24/2018.
 */

public final class ScheduleContract {
    public static final String AUTHORITY = "com.techpearl.tvguide";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_SCHEDULE = "schedule";
    private ScheduleContract(){}

    public static class ScheduleEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon()
                .appendPath(PATH_SCHEDULE)
                .build();
        public static final String TABLE_NAME = "schedule";
        public static final String COLUMN_EP_ID = "episode_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SEASON = "season";
        public static final String COLUMN_NUMBER = "number";
        public static final String COLUMN_AIR_TIME = "air_time";
        public static final String COLUMN_RUN_TIME = "run_time";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_SUMMARY = "summary";
    }
}
