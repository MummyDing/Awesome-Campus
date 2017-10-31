package cn.edu.jxnu.awesome_campus.support.utils.net.request;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.FormEncodingBuilder;
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
public class PostRequest extends IRequest{
    FormEncodingBuilder params = null;
    public PostRequest(String url){
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
        if(params == null){
            params = new FormEncodingBuilder();
            isFirstParams = false;
        }
        if(val!=null)
        params.add(key,val);
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
        Log.d("threadTTTOut", String.valueOf(Thread.currentThread()));
        childThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("threadTTTin", String.valueOf(Thread.currentThread()));
                    Response response = call.execute();
                    callback.onResponse(response);
                } catch (IOException e) {
                    callback.onFailure(e);
                }
            }
        });
        childThread.start();
        //run in child thread
       *//* mainHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    callback.onResponse(response);
                } catch (IOException e) {
                    callback.onFailure(e);
                }
            }
        });*//*
    }
*/
    private Request buildRequest(){
        if (TextUtil.isNull(url)){
            throw new IllegalArgumentException("NETWORK : url can't be null !!!!");
        }

        Request.Builder request = new Request.Builder();
        request.url(url);

        if(isFirstParams ==  false){
            request.post(params.build());
        }

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
