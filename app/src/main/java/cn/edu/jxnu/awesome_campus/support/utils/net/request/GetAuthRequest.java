package cn.edu.jxnu.awesome_campus.support.utils.net.request;

import android.util.Log;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.NetCallback;

/**
 * Created by KevinWu on 16-5-11.
 */
public class GetAuthRequest extends IRequest {
    public GetAuthRequest(String url){
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
        this.userName=userName;
        return this;
    }

    @Override
    public IRequest addPassword(String password) {
        this.password=password;
        return this;
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
        NetManageUtil.netClient.setAuthenticator(new Authenticator() {
            @Override
            public Request authenticate(Proxy proxy, Response response) throws IOException {
                String credential = Credentials.basic(userName, password);
                return response.request().newBuilder()
                        .header("Authorization", credential)
                        .build();
            }
            @Override
            public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
                return null;
            }
        });
        Call call = NetManageUtil.netClient.newCall(buildRequest());
        call.enqueue(callback);
    }

    private Request buildRequest() {
        if (TextUtil.isNull(url)) {
            new IllegalArgumentException("NETWORK : url can't be null !!!!");
            return null;
        }
        Request.Builder request = new Request.Builder();
        request.url(url)
                .get();

        // add headers
        if (headers != null) {
            for (Map.Entry<String, String> map : headers.entrySet()) {
                request.addHeader(map.getKey(), map.getValue());
            }
        }

        // add tag
        if (tag != null) {
            request.tag(tag);
        }
        return request.build();
    }
}
