package cn.edu.jxnu.awesome_campus.support.utils.net;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by MummyDing on 16-1-24.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class NetManageUtil {
    private static NetManageUtil mInstance;
    private OkHttpClient netClient;
    private Gson mGson;


    private NetManageUtil(){
        netClient = new OkHttpClient();
        //enable Cookie
        netClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mGson = new Gson();
    }

    /***
     * Singleton NetManageUtil
     * @return NetManageUtil
     */
    public synchronized static NetManageUtil getInstance(){
        if( mInstance == null){
            mInstance = new NetManageUtil();
        }
        return mInstance;
    }

    /***
     * Build a get request object
     * @return
     */
    public static GetRequest get(){
        return new GetRequest();
    }

    /***
     * Build a post request object
     * @return
     */
    public static PostRequest post(){
        return new PostRequest();
    }

    /***
     * cancel all requests
     */
    public static void cancelAll(){

    }

}
