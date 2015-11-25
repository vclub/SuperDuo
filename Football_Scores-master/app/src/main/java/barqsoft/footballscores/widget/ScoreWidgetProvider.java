package barqsoft.footballscores.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;

/**
 * Created by vclub on 15/11/24.
 */
public class ScoreWidgetProvider extends AppWidgetProvider {

    private static final String TAG = "ScoreWidgetProvider";
    private static final String ACTION_CLICK_NAME = "BARQSOFT.FOOTBALLSCORES.ACTION.WIDGET.CLICK";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive: ");
        super.onReceive(context, intent);

        if (intent.getAction().equals(ACTION_CLICK_NAME)){
            Log.e(TAG, "onReceive: click");
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.e(TAG, "onUpdate");
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views =  new RemoteViews(context.getPackageName(), R.layout.score_widget);

        views.setImageViewResource(R.id.home_crest, R.drawable.tottenham_hotspur);
        views.setTextViewText(R.id.home_name, "Tottenham Hotspur FC");

        views.setTextViewText(R.id.score_textview, "4 - 1");
        views.setTextViewText(R.id.data_textview, "00:00");

        views.setImageViewResource(R.id.away_crest, R.drawable.west_ham);
        views.setTextViewText(R.id.away_name, "West Ham United FC");

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.score_textview, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
}
