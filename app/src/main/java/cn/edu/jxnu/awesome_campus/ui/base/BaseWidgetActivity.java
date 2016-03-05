package cn.edu.jxnu.awesome_campus.ui.base;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

import org.greenrobot.eventbus.Subscribe;

import cn.edu.jxnu.awesome_campus.event.EventModel;

/**
 * Created by KevinWu on 16-3-5.
 */
public abstract class BaseWidgetActivity extends AppWidgetProvider {
    protected abstract void onEventComing(EventModel eventModel);

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {

    }

    @Override
    public void onEnabled(Context context) {

    }
    @Override
    public void onDisabled(Context context) {
    }

    @Subscribe
    public void onEventMainThread(EventModel eventModel){
        if(eventModel != null){
            onEventComing(eventModel);
        }
    }

}
