package cn.edu.jxnu.awesome_campus.support.utils.net.request;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import cn.edu.jxnu.awesome_campus.support.utils.net.callback.NetCallback;

/**
 * Created by zpauly on 16-5-12.
 */
public class PostJsonRequest {
    private static final MediaType Json = MediaType.parse("application/json; charset=utf-8");

    private String json;
    private String url;
    private OkHttpClient client;

    public PostJsonRequest addJsonObject(Object json) {
        this.json = new Gson().toJson(json);
        return this;
    }

    public PostJsonRequest addUrl(String url) {
        this.url = url;
        return this;
    }

    public void enqueue(NetCallback callback) {
        client = new OkHttpClient();
        client.newCall(buildRequest()).enqueue(callback);
    }

    private Request buildRequest() {
        RequestBody body = RequestBody.create(Json, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }
}
