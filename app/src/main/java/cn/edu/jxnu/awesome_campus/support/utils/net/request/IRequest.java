package cn.edu.jxnu.awesome_campus.support.utils.net.request;

import android.os.Handler;

import com.squareup.okhttp.OkHttpClient;

import java.util.Map;

import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.NetCallback;

/**
 * Created by MummyDing on 16-1-24.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public abstract class IRequest {
    protected OkHttpClient client = null;
    protected Map<String,String> headers = null;
    protected boolean isFirstParams = true;
    protected String url = null;
    protected String tag = null;
    public static Thread childThread;

    public abstract IRequest addHeader(String key,String val);
    public abstract IRequest addParams(String key,String val);
    public abstract IRequest addTag(String tag);
    public abstract void enqueue(NetCallback callback);
    //public abstract void execute(NetCallback callback);
}

