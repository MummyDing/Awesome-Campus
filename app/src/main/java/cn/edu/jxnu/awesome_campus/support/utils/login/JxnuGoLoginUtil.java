package cn.edu.jxnu.awesome_campus.support.utils.login;

import android.util.Log;
import android.widget.EditText;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoLoginBean;
import cn.edu.jxnu.awesome_campus.support.spkey.EducationStaticKey;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonCodeEntityCallback;

/**
 * Created by zpauly on 16-5-11.
 */
public class JxnuGoLoginUtil {
    public static final String TAG = "JxnuGoLoginUtil";

    public static String getUserName() {
        return userName;
    }

    public static String getPassWord() {
        return passWord;
    }

    private static String userName;
    private static String passWord;

    public static String getToken() {
        return token;
    }

    private static String token;

    public static String getUsername(EditText editText) {
        return editText.getText().toString();
    }

    public static String getPassword(EditText editText) {
        return editText.getText().toString();
    }

    public static void onLogin(final EditText usernameText, final EditText passwordText) {
        if (TextUtil.isNull(getUsername(usernameText)) || TextUtil.isNull(getPassword(passwordText))) {
            return;
        }
        //there has something inputed into the username and password edittext,and then verify it by net
        else{
            Log.d(TAG,"开始请求网络");
            NetManageUtil.getAuth(JxnuGoApi.LoginUrl)
                    .addTag(TAG)
                    .addUserName(getUsername(usernameText))
                    .addPassword(getPassword(passwordText))
                    .enqueue(new JsonCodeEntityCallback<JxnuGoLoginBean>() {
                        @Override
                        public void onSuccess(JxnuGoLoginBean entity, int responseCode, Headers headers) {
                            Log.d(TAG,"返回数据成功");
                            Log.d(TAG,"状态码"+responseCode);
                            if(!TextUtil.isNull(entity.getToken())){
                                Log.d("Token",entity.getToken());
                                token=entity.getToken();
                                saveToSP(getUsername(usernameText),getPassword(passwordText));

                                //登陆成功,跳转到用户信息界面
                                //EventBus.getDefault().post(new EventModel<JxnuGoLoginBean>(EVENT.JUMP_TO_JXNUGO_USERINFO,entity));
                            }
                        }

                        @Override
                        public void onFailure(String error) {
                            Log.d(TAG,"登录失败"+error);
                        }
                    });
        }
    }

    private static void saveToSP(String userName,String passWord){
        SPUtil mysp = new SPUtil(InitApp.AppContext);
        mysp.putStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME, userName);
        mysp.putStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD, passWord);

    }

}
