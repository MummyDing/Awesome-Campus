package cn.edu.jxnu.awesome_campus.support.utils.net.callback;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by MummyDing on 16-1-25.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public abstract class InputStreamCallback extends NetCallback{
    public abstract void onSuccess(InputStream result, Headers headers);
    @Override
    public void onResponse(Response response) throws IOException {
        onSuccess(response.body().byteStream(),response.headers());
    }
}
