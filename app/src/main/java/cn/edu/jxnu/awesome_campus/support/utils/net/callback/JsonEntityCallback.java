package cn.edu.jxnu.awesome_campus.support.utils.net.callback;

import com.google.gson.Gson;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

/**
 * Created by MummyDing on 16-1-25.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public abstract class JsonEntityCallback<T> extends NetCallback{
    public abstract void onSuccess(T entity, Headers headers);
    @Override
    public void onResponse(Response response){
        try {
            T entity = new Gson().fromJson(response.body().string(),((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
            onSuccess(entity,response.headers());
        } catch (Exception e) {
            onSuccess(null, null);
            e.printStackTrace();
        }
    }
}
