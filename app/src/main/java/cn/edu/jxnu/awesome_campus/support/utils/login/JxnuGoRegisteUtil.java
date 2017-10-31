package cn.edu.jxnu.awesome_campus.support.utils.login;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.EditText;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoLoginBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoRegisterBean;
import cn.edu.jxnu.awesome_campus.support.Settings;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonCodeEntityCallback;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCodeCallback;
import cn.edu.jxnu.awesome_campus.support.utils.net.request.PostJsonRequest;

/**
 * Created by root on 16-5-12.
 */
public class JxnuGoRegisteUtil {
    private static final String TAG = "JxnuGoRegisteUtil";

    public static boolean verifyPassword(EditText passwordEt, EditText verifyPasswordEt) {
        if (!(passwordEt.getText().toString().length() >= 6)) {
            DisplayUtil.Snack(passwordEt, "密码长度不得小于6位");
            passwordEt.setText("");
            verifyPasswordEt.setText("");
            return false;
        } else if (!passwordEt.getText().toString().equals(verifyPasswordEt.getText().toString())) {
            DisplayUtil.Snack(passwordEt, "两次输入密码不一致");
            passwordEt.setText("");
            verifyPasswordEt.setText("");
            return false;
        } else {
            return true;
        }
    }

    public static boolean verifyEmail(EditText emailEt) {
        String email = emailEt.getText().toString();
        String regex = "\\w+@\\w+\\.(com\\.cn)|\\w+@\\w+\\.(com|cn)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.find()) {
            return true;
        } else {
            emailEt.setText("");
            return false;
        }
    }

    public static void onRegiste(final String username, final String email, final String password) {
        final JxnuGoRegisterBean bean = new JxnuGoRegisterBean();
        bean.setUserName(username);
        bean.setUserEmail(email);
        bean.setPassWord(password);
        bean.setAuth_token(Settings.getJxnugoAuthToken());
//        final PostJsonRequest request = new PostJsonRequest(JxnuGoApi.RegisterUrl);
        NetManageUtil.postJson(JxnuGoApi.RegisterUrl)
                .addJsonObject(bean)
                .addTag(TAG)
                .enqueue(new StringCodeCallback() {
                    @Override
                    public void onSuccess(String result, int responseCode, Headers headers) {
                        Log.d(TAG,"返回码为"+responseCode);
                        Log.d(TAG,"内容为："+result);
                        if (responseCode == 200) {
                            Log.d(TAG, "注册成功");
//                            NetManageUtil.getAuth(JxnuGoApi.LoginUrl)
//                                    .addTag(TAG)
//                                    .addUserName(username)
//                                    .addPassword(password)
//                                    .enqueue(new JsonCodeEntityCallback<JxnuGoLoginBean>() {
//                                        @Override
//                                        public void onSuccess(final JxnuGoLoginBean entity, int responseCode, Headers headers) {
//                                            if (!TextUtil.isNull(entity.getToken())) {
//                                                saveToSP(entity.getToken(),
//                                                        username,
//                                                        password,
//                                                        entity.getUserId());
//                                                Log.d(TAG, "Jxnugo注册成功并登录");
                                                new Handler(Looper.getMainLooper())
                                                        .post(new Runnable() {
                                                            @Override
                                                            public void run() {
//                                                                EventBus.getDefault().post(new EventModel<Integer>(EVENT.JUMP_TO_JXNUGO
//                                                                        , entity.getUserId()));
                                                                EventBus.getDefault().post(new EventModel<Void>(EVENT.JXNUGO_REGISTER_SUCCESS));
                                                            }
                                                        });
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onFailure(String error) {
//
//                                        }
//                                    });
                        }else if(responseCode==406){
                            new Handler(Looper.getMainLooper())
                                    .post(new Runnable() {
                                        @Override
                                        public void run() {
//                                                                EventBus.getDefault().post(new EventModel<Integer>(EVENT.JUMP_TO_JXNUGO
//                                                                        , entity.getUserId()));
                                            EventBus.getDefault().post(new EventModel<Void>(EVENT.JXNUGO_REGISTER_FAILURE_SAME_NAME));
                                        }
                                    });
                        }
                        else if(responseCode==409){
                            new Handler(Looper.getMainLooper())
                                    .post(new Runnable() {
                                        @Override
                                        public void run() {
//                                                                EventBus.getDefault().post(new EventModel<Integer>(EVENT.JUMP_TO_JXNUGO
//                                                                        , entity.getUserId()));
                                            EventBus.getDefault().post(new EventModel<Void>(EVENT.JXNUGO_REGISTER_FAILURE_SAME_EMAIL));
                                        }
                                    });
                        }
                        else{
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<Void>(EVENT.JXNUGO_REGISTER_FAILURE));
                                }
                            });
                        }
                    }
                    @Override
                    public void onFailure(String error) {
                        Log.i(TAG, "error");
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<Void>(EVENT.JXNUGO_REGISTER_FAILURE));
                            }
                        });
                    }
                });
    }

    private static void saveToSP(String token, String userName, String passWord, int userId) {
        SPUtil mysp = new SPUtil(InitApp.AppContext);
        mysp.putStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.TOKEN, token);
        mysp.putStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME, userName);
        mysp.putStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD, passWord);
        mysp.putIntSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERID, userId);

    }
}
