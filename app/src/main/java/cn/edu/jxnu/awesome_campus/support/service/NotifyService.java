package cn.edu.jxnu.awesome_campus.support.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.about.NotifyBean;
import cn.edu.jxnu.awesome_campus.model.about.NotifyModel;
import cn.edu.jxnu.awesome_campus.model.life.WeatherInfoModel;
import cn.edu.jxnu.awesome_campus.support.boardcast.NotifyClickReceiver;
import cn.edu.jxnu.awesome_campus.support.utils.common.NotifyUtil;

public class NotifyService extends Service {

    public static final String ACTION = "cn.edu.jxnu.awesome_campus.support.service";

    private NotificationManager manager;
    private NotificationCompat.Builder builder;
    private PollingThread pollingThread;
    private NotifyModel notifyModel = new NotifyModel();
    private WeatherInfoModel weatherInfoModel = new WeatherInfoModel();
    private List<NotifyModel>  modelList;

    public NotifyService() {
        initNotifyManager();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        pollingThread = new PollingThread();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        pollingThread = new PollingThread();
        pollingThread.start();
        Log.d("msg","start");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw null;
    }


    private void initNotifyManager(){
        manager = (NotificationManager) InitApp.AppContext.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.logo);
        builder.setContentTitle(InitApp.AppContext.getString(R.string.app_name));
        builder.setLargeIcon(BitmapFactory.decodeResource(InitApp.AppContext.getResources(),R.drawable.logo));
        builder.setAutoCancel(true);
    }


    private void showNotify(NotifyModel model){
        builder.setContentText(model.getTitle());

        Intent intent = new Intent(this, NotifyClickReceiver.class);
        intent.putExtra(getString(R.string.id_type),model.getType());
        intent.putExtra(getString(R.string.id_data),model.getData());
        intent.putExtra(getString(R.string.id_title),model.getTitle());

        PendingIntent pIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pIntent);
        manager.cancel(0);
        manager.notify(0,builder.build());
    }

    /**
     * Polling thread
     * 向Server轮询的异步线程
     */
    class PollingThread extends Thread {
        @Override
        public void run() {
            // 这里请求消息
             notifyModel.loadFromCache();
            // 刷新天气
            weatherInfoModel.loadFromNet();
        }
    }

    @Subscribe
    public void onEventMainThread(EventModel eventModel) {
        switch (eventModel.getEventCode()) {
            case EVENT.NOTIFY_LOAD_CACHE_SUCCESS:
                modelList = (List<NotifyModel>) eventModel.getDataList();
                notifyModel.loadFromNet();
                notifyUpdateMenu(modelList.size());
                break;
            case EVENT.NOTIFY_LOAD_CACHE_FAILURE:
                notifyModel.loadFromNet();
                break;
            case EVENT.NOTIFY_REFRESH_SUCCESS:

                NotifyBean notifyBean = (NotifyBean) eventModel.getData();
                if (NotifyUtil.checkVersion(notifyBean.getNotifyVersion())){
                    break;
                }
                NotifyUtil.updateNotifyVersion(notifyBean.getNotifyVersion());

                modelList = Arrays.asList(notifyBean.getMsgList());
                notifyModel.cacheAll(modelList);
                notifyUpdateMenu(modelList.size());
                showNotify(modelList.get(0));
                break;
            case EVENT.NOTIFY_REFRESH_FAILURE:
                break;
        }
    }


    private void notifyUpdateMenu(int flag){
        EventBus.getDefault().post(new EventModel<Integer>(EVENT.UPDATE_MENU,flag));
    }
    @Override
    public void onDestroy () {
        EventBus.getDefault().unregister(this);
         pollingThread.interrupt();
        super.onDestroy();
    }
}
