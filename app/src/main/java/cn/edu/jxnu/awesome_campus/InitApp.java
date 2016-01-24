package cn.edu.jxnu.awesome_campus;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;

/**
 * Created by MummyDing on 16-1-25.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class InitApp extends Application{
    public static NetManageUtil netClient;
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        netClient = NetManageUtil.getInstance();
        Fresco.initialize(getAppContext());
    }

    public static Context getAppContext(){
        if(mContext == null){
            mContext = getAppContext();
        }
        return mContext;
    }
}
