package cn.edu.jxnu.awesome_campus.ui.base;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cn.edu.jxnu.awesome_campus.support.utils.common.TimeUtil;

/**
 * Created by KevinWu on 16-3-5.
 */
public abstract class BaseWidgetPrivider extends AppWidgetProvider {
    protected Object object=null;
    protected int reFreshTime=1000;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        init();
        Intent intent = new Intent();
        intent.setClass(context, (Class<BaseWidgetService>) object);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        //定时更新
        AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC, TimeUtil.getTimestamp(), reFreshTime, pendingIntent);
        Log.d("测试","--"+this.getClass());
    }

    protected abstract void init();
}
