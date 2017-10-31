package cn.edu.jxnu.awesome_campus.support.utils.net.request;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Request;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.NetCallback;

/**
 * Created by MummyDing on 16-1-24.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class GetRequest extends IRequest {

    public GetRequest(String url){
        this.url = url;
    }
    @Override
    public IRequest addHeader(String key, String val) {
        if (headers == null){
            headers = new LinkedHashMap<>();
        }
        headers.put(key,val);
        return this;
    }

    @Override
    public IRequest addParams(String key, String val) {
        if(TextUtil.isNull(url)){
            throw new IllegalArgumentException("NETWORK : url can't be null !!!!");
        }
        if (isFirstParams){
            url += "?";
            isFirstParams = false;
        }else {
            url += "&";
        }
        url = url+key+"="+val;
        return this;
    }

    @Override
    public IRequest addUserName(String userName) {
        return null;
    }

    @Override
    public IRequest addPassword(String password) {
        return null;
    }

    @Override
    public IRequest addJsonObject(Object json) {
        return null;
    }

    @Override
    public IRequest addTag(String tag) {
        this.tag = tag;
        return this;
    }


    @Override
    public void enqueue(NetCallback callback) {
        Call call = NetManageUtil.netClient.newCall(buildRequest());
        call.enqueue(callback);
    }

   /* @Override
    public void execute(final NetCallback callback) {
        final Call call = NetManageUtil.netClient.newCall(buildRequest());
        //run in child thread
        //Log.d("threadTTTOut", String.valueOf(Thread.currentThread()));
        childThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    Log.d("eeee","okok");
                  //  Log.d("threadTTTin", response.body().string());
                    callback.onResponse(response);
                } catch (IOException e) {
                    callback.onFailure(e);
                }
            }
        });
        childThread.start();
    }*/

    private Request buildRequest(){
        if (TextUtil.isNull(url)){
            new IllegalArgumentException("NETWORK : url can't be null !!!!");
            return null;
        }
        Request.Builder request = new Request.Builder();
        request.url(url)
                .get();

        // add headers
        if(headers != null){
            for(Map.Entry<String ,String> map: headers.entrySet()){
                request.addHeader(map.getKey(),map.getValue());
            }
        }

        // add tag
        if(tag != null){
            request.tag(tag);
        }

        return request.build();
    }
}
