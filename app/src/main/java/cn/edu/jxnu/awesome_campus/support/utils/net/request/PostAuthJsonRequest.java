package cn.edu.jxnu.awesome_campus.support.utils.net.request;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.Proxy;

import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.NetCallback;

/**
 * Created by KevinWu on 16-5-15.
 */
public class PostAuthJsonRequest extends PostRequest {
    public static final String TAG="PostAuthJsonRequest";
    private static final MediaType Json = MediaType.parse("application/json; charset=utf-8");


    public PostAuthJsonRequest(String url) {
        super(url);
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
        Log.d(TAG,"用户名"+userName);
        Log.d(TAG,"密码"+password);
        Call call = NetManageUtil.netClient.newCall(buildRequest());
        call.enqueue(callback);
    }

    private Request buildRequest() {
        RequestBody body = RequestBody.create(Json, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
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
    public IRequest addJsonObject(Object bean) {
        this.json = new Gson().toJson(bean);
        Log.d("封装后的Json数据为","--"+json.toString());
        return this;
    }
}
