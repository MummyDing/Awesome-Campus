package cn.edu.jxnu.awesome_campus.ui.base;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.edu.jxnu.awesome_campus.event.EventModel;

/**
 * Created by KevinWu on 16-3-6.
 */
public abstract class BaseWidgetService extends Service{
    protected Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("新建服务","--");
        EventBus.getDefault().register(this);
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        init();
        return super.onStartCommand(intent, flags, startId);
    }

    //初始化方法
    protected abstract void init();

    //更新方法
    protected abstract void updateWidget();
    protected abstract void onEventComing(EventModel eventModel);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Subscribe
    public void onEventMainThread(EventModel eventModel){
        if(eventModel != null){
            onEventComing(eventModel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
