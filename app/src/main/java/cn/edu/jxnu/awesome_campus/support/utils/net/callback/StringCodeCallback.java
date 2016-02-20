package cn.edu.jxnu.awesome_campus.support.utils.net.callback;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by MummyDing on 16-2-20.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public abstract class StringCodeCallback extends NetCallback{

    public abstract void onSuccess(String result, int responseCode,Headers headers);
    public abstract void onFailure(String error);
    @Override
    public void onResponse(final Response response) throws IOException {
        onSuccess(response.body().string(),response.code(),response.headers());
    }

    @Override
    public void onFailure(final IOException e) {
        onFailure(e.toString());
    }
}
