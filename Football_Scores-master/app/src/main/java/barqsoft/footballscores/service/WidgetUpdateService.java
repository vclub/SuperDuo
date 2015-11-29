package barqsoft.footballscores.service;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.scoresAdapter;
import barqsoft.footballscores.widget.ScoreWidgetProvider;

/**
 * Created by Bin Li on 2015/11/29.
 */
public class WidgetUpdateService extends Service {

    public static final String ACTION_NEXT_SCORE = "barqsoft.footballscores.appwidget.ACTION_NEXT_SCORE";

    private static int sCursorNumber;

    public static int getCursorNumber() {
        return sCursorNumber;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        RemoteViews views = new RemoteViews(getPackageName(), R.layout.score_widget);
        Cursor cursor = getContentResolver().query(
                DatabaseContract.BASE_CONTENT_URI,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToPosition(sCursorNumber);

            views.setTextViewText(R.id.home_name, cursor.getString(scoresAdapter.COL_HOME));
            views.setTextViewText(R.id.away_name, cursor.getString(scoresAdapter.COL_AWAY));

            views.setTextViewText(R.id.data_textview, cursor.getString(scoresAdapter.COL_MATCHTIME));
            views.setTextViewText(R.id.score_textview, Utilies.getScores(cursor.getInt(scoresAdapter.COL_HOME_GOALS), cursor.getInt(scoresAdapter.COL_AWAY_GOALS)));

            views.setImageViewResource(R.id.home_crest, Utilies.getTeamCrestByTeamName(
                    cursor.getString(scoresAdapter.COL_HOME)));
            views.setImageViewResource(R.id.away_crest, Utilies.getTeamCrestByTeamName(
                    cursor.getString(scoresAdapter.COL_AWAY)
            ));

            if (sCursorNumber + 1 < cursor.getCount()) {
                sCursorNumber++;
            } else {
                sCursorNumber = 0;
            }
        }

        PendingIntent refreshIntent = PendingIntent.getService(this, 0, new Intent(this, WidgetUpdateService.class), 0);
        views.setOnClickPendingIntent(R.id.btn_refresh, refreshIntent);

        PendingIntent appIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        views.setOnClickPendingIntent(R.id.container, appIntent);

        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        ComponentName widget = new ComponentName(this, ScoreWidgetProvider.class);
        manager.updateAppWidget(widget, views);

        Intent broadcast = new Intent(ACTION_NEXT_SCORE);
        sendBroadcast(broadcast);

        stopSelf();
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
