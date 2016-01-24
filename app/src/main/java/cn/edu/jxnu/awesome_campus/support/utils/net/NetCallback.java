package cn.edu.jxnu.awesome_campus.support.utils.net;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by MummyDing on 16-1-24.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public abstract class NetCallback implements Callback {

    public abstract void onFailure(IOException e);
    public abstract void onSuccess(String result, InputStream resultIS);
    @Override
    public void onFailure(Request request, IOException e) {
        onFailure(e);
    }

    @Override
    public void onResponse(Response response) throws IOException {
        onSuccess(response.body().string(),response.body().byteStream());
    }
}
