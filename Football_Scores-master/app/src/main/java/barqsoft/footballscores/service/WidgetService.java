package barqsoft.footballscores.service;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.scoresAdapter;

/**
 * Created by vclub on 15/12/1.
 */
@SuppressLint("NewApi")
public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this, intent);
    }

    private class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context mContext;
        private int mAppWidgetId;

        private Cursor mDataCursor;

        public ListRemoteViewsFactory(Context context, Intent intent) {
            mContext = context.getApplicationContext();
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            mDataCursor = getContentResolver().query(
                    DatabaseContract.BASE_CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        public void onDataSetChanged() {
            mDataCursor = getContentResolver().query(
                    DatabaseContract.BASE_CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        public void onDestroy() {
            mDataCursor.close();
            mDataCursor = null;
        }

        @Override
        public int getCount() {
            return mDataCursor.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            mDataCursor.moveToPosition(position);

            RemoteViews views = new RemoteViews(getPackageName(), R.layout.scores_widget_list_item);
            views.setTextViewText(R.id.home_name, mDataCursor.getString(scoresAdapter.COL_HOME));
            views.setTextViewText(R.id.away_name, mDataCursor.getString(scoresAdapter.COL_AWAY));

            views.setTextViewText(R.id.data_textview, mDataCursor.getString(scoresAdapter.COL_MATCHTIME));
            views.setTextViewText(R.id.score_textview, Utilies.getScores(mDataCursor.getInt(scoresAdapter.COL_HOME_GOALS), mDataCursor.getInt(scoresAdapter.COL_AWAY_GOALS)));

            views.setImageViewResource(R.id.home_crest, Utilies.getTeamCrestByTeamName(
                    mDataCursor.getString(scoresAdapter.COL_HOME)));
            views.setImageViewResource(R.id.away_crest, Utilies.getTeamCrestByTeamName(
                    mDataCursor.getString(scoresAdapter.COL_AWAY)));

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
