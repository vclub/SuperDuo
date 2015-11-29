package barqsoft.footballscores.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import barqsoft.footballscores.service.WidgetUpdateService;

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
        context.startService(new Intent(context, WidgetUpdateService.class));
    }

}
