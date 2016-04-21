package cn.edu.jxnu.awesome_campus.support.utils.login;

import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.EditText;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.support.spkey.SelfStudyRoomStaticKey;
import cn.edu.jxnu.awesome_campus.support.urlconfig.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCodeCallback;

/**
 * Created by KevinWu on 16-4-21.
 */
public class SelfStudyRoomLoginUtil {
    private static final String TAG="SelfStudyRoomLoginUtil";
    public static String cookie;
    public static final String VIEWSTATE="/wEPDwUKLTI1Nzg1ODIyMGQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgEFEGNoa19SZW1QYXNzcHdvcmTiU5zolo6/Gtin2EhtwQjwibMyu11t2YOmrWpFNSXQOw==";
    public static final String VIEWSTATEGENERATOR="C2EE9ABB";
    public static final String EVENTVALIDATION="/wEWBALGu8H0CwK1lMLgCgLS9cL8AgKXzJ6eD1PrwC/+tEuQt/W6kERZa2FJGBofrpzrzMbXnOcWuVzp";


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
     * 登录方法
     * @param usernameET
     * @param passwordET
     */
    public static void onLogin(EditText usernameET,EditText passwordET){
        if (TextUtil.isNull(getUsername(usernameET)) || TextUtil.isNull(getPassword(passwordET))) {
            EventBus.getDefault().post(new EventModel<String>(EVENT.SELFSTUDYROOM_LOGIN_FAILURE_NULL_INPUT));
            return;
        } else {
            NetManageUtil.post(Urlconfig.SelfStudyRoom_Login_URL)
                    .addTag(TAG)
                    .addParams("__VIEWSTATE",VIEWSTATE)
                    .addParams("__VIEWSTATEGENERATOR",VIEWSTATEGENERATOR)
                    .addParams("__EVENTVALIDATION",EVENTVALIDATION)
                    .addParams("subCmd","Login")
                    .addParams("txt_LoginID",getUsername(usernameET))
                    .addParams("txt_Password",getPassword(passwordET))
                    .enqueue(new StringCodeCallback() {
                        @Override
                        public void onSuccess(String result, int code,Headers headers) {
                            System.out.println("返回结果的状态码为："+code);
//                            System.out.println("头部信息为：\n"+headers.toString());
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
                                toFollowRedirects(cookies);
                            }else{
                                //密码错误
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<String>(EVENT.SELFSTUDYROOM_LOGIN_FAILURE));
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(String error) {
                            System.out.println("登录失败");
                        }
                    });


        }
    }

    private static void toFollowRedirects(String cookies) {
        Log.d("自习室重定向", "------");
        NetManageUtil.get(Urlconfig.SelfStudyRoom_Redirect_URL)
                .addHeader("Cookie", cookies)
                .addTag(TAG)
                .enqueue(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Headers headers) {

                        if(result.indexOf("注销")>0){
                            Log.d("自习室重定向成功", "---");
                            saveToSP(cookie);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<String>(EVENT.SELFSTUDYROOM_LOGIN_SUCCESS));
                            }
                        });
                        }else{
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<String>(EVENT.SELFSTUDYROOM_LOGIN_FAILURE));
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.d("自习室重定向失败", "---");
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<String>(EVENT.SELFSTUDYROOM_LOGIN_FAILURE_NETWORKERROR));
                            }
                        });
                    }
                });
    }
    private static void saveToSP(String cookies) {
        SPUtil mysp = new SPUtil(InitApp.AppContext);
        SelfStudyRoomLoginUtil.cookie=cookies;


        mysp.putStringSP(SelfStudyRoomStaticKey.SP_FILE_NAME, SelfStudyRoomStaticKey.COOKIE, cookies);
    }
}
