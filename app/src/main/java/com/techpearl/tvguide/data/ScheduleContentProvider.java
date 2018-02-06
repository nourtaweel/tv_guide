package com.techpearl.tvguide.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Nour on 1/29/2018.
 */

public class ScheduleContentProvider extends ContentProvider {
    private ScheduleOpenHelper mOpenHelper;

    private static final int CODE_SCHEDULE = 100;
    private static final int CODE_SCHEDULE_WITH_ID = 101;

    private static final UriMatcher sMatcher = buildMatcher();

    private static UriMatcher buildMatcher(){
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        //whole directory
        matcher.addURI(ScheduleContract.AUTHORITY, ScheduleContract.PATH_SCHEDULE, CODE_SCHEDULE);
        //single item
        matcher.addURI(ScheduleContract.AUTHORITY, ScheduleContract.PATH_SCHEDULE + "/#"
                , CODE_SCHEDULE_WITH_ID);
        return matcher;
    }
    @Override
    public boolean onCreate() {
        mOpenHelper = new ScheduleOpenHelper(this.getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        int match = sMatcher.match(uri);
        Cursor retCursor;
        switch (match){
            case CODE_SCHEDULE:
                //content://<authority>/schedule
                retCursor = mOpenHelper.getReadableDatabase().query(
                        ScheduleContract.ScheduleEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
                break;
            case CODE_SCHEDULE_WITH_ID:
                //content://<authority>/schedule/id
                String id = uri.getPathSegments().get(1);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        ScheduleContract.ScheduleEntry.TABLE_NAME,
                        null,
                        ScheduleContract.ScheduleEntry._ID + "?",
                        new String[]{id},
                        null,
                        null,
                        null);
                break;
                default:
                    throw new UnsupportedOperationException("Unsupported query on uri " + uri.toString());
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        switch (sMatcher.match(uri)){
            case CODE_SCHEDULE:
                int rowsInserted = 0;
                SQLiteDatabase db = mOpenHelper.getWritableDatabase();
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(
                                ScheduleContract.ScheduleEntry.TABLE_NAME,
                                null,
                                value);
                        if (_id > 0)
                            rowsInserted++;
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                if(rowsInserted > 0 )
                    getContext().getContentResolver().notifyChange(uri, null);
                return rowsInserted;
            default:
                return super.bulkInsert(uri, values);
        }
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = sMatcher.match(uri);
        switch (match){
            case CODE_SCHEDULE:
                SQLiteDatabase db = mOpenHelper.getWritableDatabase();
                return db.delete(ScheduleContract.ScheduleEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new UnsupportedOperationException("deleting unsupported for uri " +
                            uri.toString());
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
