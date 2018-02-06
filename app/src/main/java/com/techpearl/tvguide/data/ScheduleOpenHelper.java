package com.techpearl.tvguide.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nour on 1/24/2018.
 */

public class ScheduleOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "schedule.db";
    private static final int DB_VERSION = 2;
    public ScheduleOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createStatement = "CREATE TABLE " + ScheduleContract.ScheduleEntry.TABLE_NAME
                + " ( " + ScheduleContract.ScheduleEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ScheduleContract.ScheduleEntry.COLUMN_EP_ID + " TEXT NOT NULL, "
                + ScheduleContract.ScheduleEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + ScheduleContract.ScheduleEntry.COLUMN_SEASON + " TEXT, "
                + ScheduleContract.ScheduleEntry.COLUMN_NUMBER + " TEXT, "
                + ScheduleContract.ScheduleEntry.COLUMN_AIR_TIME + " TEXT NOT NULL, "
                + ScheduleContract.ScheduleEntry.COLUMN_RUN_TIME + " TEXT NOT NULL, "
                + ScheduleContract.ScheduleEntry.COLUMN_SUMMARY + " TEXT, "
                + ScheduleContract.ScheduleEntry.COLUMN_IMAGE + " TEXT)";
        sqLiteDatabase.execSQL(createStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ScheduleContract.ScheduleEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
