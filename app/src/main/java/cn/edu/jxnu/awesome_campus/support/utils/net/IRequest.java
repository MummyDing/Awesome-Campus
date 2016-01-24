package cn.edu.jxnu.awesome_campus.support.utils.net;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MummyDing on 16-1-24.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public abstract class IRequest {
    protected Map<String,String> headers = null;
    protected Map<String,String> params = null;
    protected String url = null;

    public abstract IRequest addHeader(String key,String val);
    public abstract IRequest addParams(String key,String val);
    public abstract IRequest addUrl(String url);
    public abstract void execute(NetCallback callback);
}
