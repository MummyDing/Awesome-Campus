package cn.edu.jxnu.awesome_campus.support.utils.net.request;

import com.squareup.okhttp.OkHttpClient;

import java.util.Map;

import cn.edu.jxnu.awesome_campus.support.utils.net.callback.NetCallback;

/**
 * Created by MummyDing on 16-1-24.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public abstract class IRequest {
    protected Map<String,String> headers = null;
    protected boolean isFirstParams = true;
    protected String url = null;
    protected String tag = null;
    protected String userName;
    protected String password;
    protected String json;
    public abstract IRequest addHeader(String key,String val);
    public abstract IRequest addParams(String key,String val);
    public abstract IRequest addUserName(String userName);
    public abstract IRequest addPassword(String password);
    public abstract IRequest addJsonObject(Object bean);
    public abstract IRequest addTag(String tag);
    public abstract void enqueue(NetCallback callback);
    //public abstract void execute(NetCallback callback);
}

