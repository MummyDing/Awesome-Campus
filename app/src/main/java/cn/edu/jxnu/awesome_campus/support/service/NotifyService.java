package cn.edu.jxnu.awesome_campus.support.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.about.NotifyModel;
import cn.edu.jxnu.awesome_campus.support.boardcast.NotifyClickReceiver;

public class NotifyService extends Service {


    private NotificationManager manager;
    private NotificationCompat.Builder builder;
    private PollingThread pollingThread;
    public NotifyService() {
        initNotifyManager();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        pollingThread = new PollingThread();
        pollingThread.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    private void initNotifyManager(){
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.logo);
        builder.setContentTitle(getString(R.string.app_name));
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo));
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

    @Subscribe
    public void onEventMainThread(EventModel eventModel){
        if(eventModel != null){
           // 处理...
        }
    }

    /**
     * Polling thread
     * 模拟向Server轮询的异步线程
     */
    static class PollingThread extends Thread {
        @Override
        public void run() {
            // 这里请求消息
            /*System.out.println("Polling...");
            count ++;
            //当计数能被5整除时弹出通知
            if (count % 5 == 0) {
                showNotification();
                System.out.println("New message!");
            }*/
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        pollingThread.interrupt();
        super.onDestroy();
    }
}
