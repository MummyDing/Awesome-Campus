package cn.edu.jxnu.awesome_campus.support.utils.net;

import android.content.Context;
import android.net.ConnectivityManager;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.net.CookieManager;
import java.net.CookiePolicy;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.support.utils.net.request.GetAuthRequest;
import cn.edu.jxnu.awesome_campus.support.utils.net.request.GetRequest;
import cn.edu.jxnu.awesome_campus.support.utils.net.request.PostAuthJsonRequest;
import cn.edu.jxnu.awesome_campus.support.utils.net.request.PostJsonRequest;
import cn.edu.jxnu.awesome_campus.support.utils.net.request.PostRequest;

/**
 * Created by MummyDing on 16-1-24.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 * @link {https://github.com/MummyDing/Awesome-Campus/blob/dev/Doc/NetManageUtil%E8%AF%B4%E6%98%8E.md}
 */
public class NetManageUtil {
    public static OkHttpClient netClient;
    private static Gson mGson;

    static {
        netClient = new OkHttpClient();
        netClient.setFollowRedirects(false);
        mGson = new Gson();
    }

    private NetManageUtil(){

    }


    /***
     * Build a get request object
     * @return
     */
    public static GetRequest get(String url){
        return new GetRequest(url);
    }

    public static GetAuthRequest getAuth(String url){
        return new GetAuthRequest(url);
    }

    /***
     * Build a post request object
     * @return
     */
    public static PostRequest post(String url){
        return new PostRequest(url);
    }

    public static PostJsonRequest postJson(String url){
        return new PostJsonRequest(url);
    }
    public static PostAuthJsonRequest postAuthJson(String url){
        return new PostAuthJsonRequest(url);
    }

    /***
     * cancel requests by tag
     */

    public static void cancelByTag(String tag){
        netClient.cancel(tag);
    }



    public static boolean isWIFI = false;

    /**
     * 读取网络状态
     * @return
     */
    public static boolean readNetworkState() {

        ConnectivityManager cm = (ConnectivityManager) InitApp.AppContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
            isWIFI = (cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI);
            return true;
        } else {
            return false;
        }
    }

}
