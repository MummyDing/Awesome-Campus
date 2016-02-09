package cn.edu.jxnu.awesome_campus.support.utils.net.callback;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by MummyDing on 16-1-25.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public abstract class StringCallback extends NetCallback{
    public abstract void onSuccess(String result, Headers headers);
    public abstract void onFailure(String error);
    @Override
    public void onResponse(final Response response) throws IOException {
        onSuccess(response.body().string(),response.headers());
    }

    @Override
    public void onFailure(final IOException e) {
        onFailure(e.toString());
    }
}
