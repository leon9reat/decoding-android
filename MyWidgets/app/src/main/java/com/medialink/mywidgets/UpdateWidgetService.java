package com.medialink.mywidgets;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

public class UpdateWidgetService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        Context context;
        RemoteViews view = new RemoteViews(getPackageName(), R.layout.random_numbers_widget);
        ComponentName theWidget = new ComponentName(this,RandomNumbersWidget.class );
        String lastUpdate = String.format("Random: %d", NumberGenerator.Generate(100));
        view.setTextViewText(R.id.appwidget_text, lastUpdate);
        manager.updateAppWidget(theWidget, view);
        jobFinished(params, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
