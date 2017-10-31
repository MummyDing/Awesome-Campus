package cn.edu.jxnu.awesome_campus.support.utils.login;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.EditText;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.support.spkey.LibraryStaticKey;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.libary.LibraryLoginInfoParse;
import cn.edu.jxnu.awesome_campus.support.config.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCodeCallback;

/**
 * Created by MummyDing on 16-2-17.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class LibraryLoginUtil {
    private static final String TAG = "LibraryLoginUtil";
    public static String cookies;
    public static String userName;
    public static String id;
    public static String password;

    private static String getUsername(EditText usernameET) {
        if (usernameET == null) {
            throw new IllegalArgumentException("args cannot be null");
        }
        return usernameET.getText().toString();
    }

    private static String getPassword(EditText passwordET) {
        if (passwordET == null) {
            throw new IllegalArgumentException("args cannot be null");
        }
        return passwordET.getText().toString();
    }

    /**
     * 这个就是放在登陆界面调用的
     *
     * @param usernameET
     * @param passwordET
     */
    public static void onLogin(final EditText usernameET, final EditText passwordET) {
        if (TextUtil.isNull(getUsername(usernameET)) || TextUtil.isNull(getPassword(passwordET))) {
            EventBus.getDefault().post(new EventModel<String>(EVENT.LIBRARY__LOGIN_FAILURE_NULL_INPUT));
            return;
        } else {
            NetManageUtil.post(Urlconfig.Library_Login_URL)
                    .addTag(TAG)
                    .addParams("number", getUsername(usernameET))
                    .addParams("passwd", getPassword(passwordET))
                    .addParams("select", "cert_no")
                    .addParams("returnUrl", "")
                    .enqueue(new StringCodeCallback() {
                        @Override
                        public void onSuccess(String result,int code, Headers headers) {
                            System.out.println("获取到的状态码为：" + code);

                            String cookies = null;
                            for (int i = 0; i < headers.size(); i++) {
                                if (headers.name(i).equals("Set-Cookie")) {
                                    cookies = headers.value(i);
                                    break;
                                }
                            }
                            System.out.println(cookies);
                            //账号密码正确，执行重定向
                            if(code==302){
                                toFollowRedirects(getUsername(usernameET),getPassword(passwordET),cookies);
                            }else{
                                //密码错误
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<String>(EVENT.LIBRARY_LOGIN_FAILURE));
                                    }
                                });
                            }

                        }

                        @Override
                        public void onFailure(String error) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<String>(EVENT.LIBRARY_LOGIN_FAILURE_NETWORKERROR));
                                }
                            });
                        }
                    });
        }
    }

    private static void toFollowRedirects(final String id, final String password, final String cookies) {
        Log.d("调用到这里", "------");
        NetManageUtil.get(Urlconfig.Library_Redirect_URL)
                .addHeader("Cookie", cookies)
                .addTag(TAG)
                .enqueue(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Headers headers) {
                        Log.d("第二次访问成功", "---");
                        LibraryLoginInfoParse myParse=new LibraryLoginInfoParse(result);
                        String name=myParse.getEndList().get(0).toString();
                        saveToSP(id,password,name,cookies);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<String>(EVENT.LIBRARY_LOGIN_SUCCESS));
                            }
                        });
                     }

                    @Override
                    public void onFailure(String error) {
                        Log.d("第二次访问失败", "---");
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<String>(EVENT.LIBRARY_LOGIN_FAILURE_NETWORKERROR));
                            }
                        });
                    }
                });
    }

    private static void saveToSP(String id,String password,String userName, String cookies) {
        SPUtil mysp = new SPUtil(InitApp.AppContext);
        LibraryLoginUtil.cookies=cookies;
        //用户名还需存入sp文件
        mysp.putStringSP(LibraryStaticKey.SP_FILE_NAME,LibraryStaticKey.USER_NAME,userName);
        mysp.putStringSP(LibraryStaticKey.SP_FILE_NAME, LibraryStaticKey.COOKIE, cookies);
        mysp.putStringSP(LibraryStaticKey.SP_FILE_NAME,LibraryStaticKey.ID,id);
        mysp.putStringSP(LibraryStaticKey.SP_FILE_NAME,LibraryStaticKey.PASSWORD,password);
    }


    /**
     * 这个方法是根据当前cookie是否存在来判断当前是否处于登陆状态【查询本地sp即可】
     *
     * @return
     */

    public static boolean isLogin() {

//        Log.d("执行到判断是否登录的方法", "--");
       SPUtil sp = new SPUtil(InitApp.AppContext);
        String cookie = sp.getStringSP(LibraryStaticKey.SP_FILE_NAME, LibraryStaticKey.COOKIE);
        if (TextUtil.isNull(cookie) == false) {
//            Log.d("已登录","--");
            cookies=sp.getStringSP(LibraryStaticKey.SP_FILE_NAME, LibraryStaticKey.COOKIE);
            // 获取cookie
            userName=sp.getStringSP(LibraryStaticKey.SP_FILE_NAME,LibraryStaticKey.USER_NAME);
            id=sp.getStringSP(LibraryStaticKey.SP_FILE_NAME,LibraryStaticKey.ID);
            password=sp.getStringSP(LibraryStaticKey.SP_FILE_NAME,LibraryStaticKey.PASSWORD);
            return true;
        }
//        Log.d("未登录","--");
        return false;
    }

    /**
     * 清除本地Cookie，主要应用场景:
     * 1. 注销登陆
     * 2. 当前cookie失效
     */
    public static void clearCookie() {
        SPUtil sp=new SPUtil(InitApp.AppContext);
        sp.clearSP(LibraryStaticKey.SP_FILE_NAME);
        userName=null;
        cookies=null;
    }

    /**
     * 对于已经登录的通过这个取得cookies
     */
    public static void getCookie(){
        NetManageUtil.post(Urlconfig.Library_Login_URL)
                .addTag(TAG)
                .addParams("number", id)
                .addParams("passwd", password)
                .addParams("select", "cert_no")
                .addParams("returnUrl", "")
                .enqueue(new StringCodeCallback() {
                    @Override
                    public void onSuccess(String result,int code, Headers headers) {
                        System.out.println("获取到的状态码为：" + code);

                        String cookies = null;
                        for (int i = 0; i < headers.size(); i++) {
                            if (headers.name(i).equals("Set-Cookie")) {
                                cookies = headers.value(i);
                                break;
                            }
                        }
//                        System.out.println(cookies);
                        //账号密码正确，执行重定向
                        if(code==302){
                            final String finalCookies = cookies;
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<String>(EVENT.LIBRARY_GET_COOKIE_SUCCESS, finalCookies));
                                }
                            });
                        }else{
                            //密码错误
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<String>(EVENT.LIBRARY_LOGIN_FAILURE));
                                }
                            });
                        }

                    }

                    @Override
                    public void onFailure(String error) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<String>(EVENT.LIBRARY_LOGIN_FAILURE_NETWORKERROR));
                            }
                        });
                    }
                });
    }

}
