package cn.edu.jxnu.awesome_campus.support.utils.net.callback;

import com.google.gson.Gson;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

/**
 * Created by KevinWu on 16-5-11.
 */
public abstract class JsonCodeEntityCallback<T> extends NetCallback {

    public abstract void onSuccess(T entity, int responseCode,Headers headers);
    public abstract void onFailure(String error);
    @Override
    public void onResponse(Response response) throws IOException {
        T entity = new Gson().fromJson(response.body().string(),((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        onSuccess(entity,response.code(),response.headers());
    }
    @Override
    public void onFailure(final IOException e) {
        onFailure(e.toString());
    }
}
