package com.techpearl.tvguide.sync;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Nour on 2/5/2018.
 */

public class TvGuideSyncUtils {
    public static void startImmediateSync(Context context){
        Intent syncIntent = new Intent(context, TvGuideSyncIntentService.class);
        context.startService(syncIntent);
    }
}
