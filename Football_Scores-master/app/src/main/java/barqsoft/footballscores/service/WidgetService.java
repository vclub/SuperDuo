package barqsoft.footballscores.service;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.RemoteViewsService;

import barqsoft.footballscores.widget.WidgetDataProvider;

/**
 * Created by vclub on 15/12/1.
 */
@SuppressLint("NewApi")
public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        WidgetDataProvider dataProvider = new WidgetDataProvider(
                getApplicationContext(), intent
        );

        return dataProvider;
    }
}
