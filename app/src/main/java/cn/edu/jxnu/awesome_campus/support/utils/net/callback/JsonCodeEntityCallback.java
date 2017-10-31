package cn.edu.jxnu.awesome_campus.support.utils.net.callback;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
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
    public void onResponse(Response response) throws IOException{
        try{
            T entity = new Gson().fromJson(response.body().string(),((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
            onSuccess(entity,response.code(),response.headers());
        }catch (JsonSyntaxException e){
            Log.d("--","异常"+e);
            onFailure("数据解析出错");
        }
    }
    @Override
    public void onFailure(final IOException e) {
        onFailure(e.toString());
    }
}
