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
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Fresco.initialize(mContext);

    }
}
