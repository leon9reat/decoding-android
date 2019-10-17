package com.medialink.submission5.widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class MovieWidgetService extends RemoteViewsService {

    private static final String TAG = "MovieWidgetService";

    public MovieWidgetService() {
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "onGetViewFactory: diproses ga sih?");
        return new MovieRemoteViewFactory(this.getApplicationContext());
    }
}
