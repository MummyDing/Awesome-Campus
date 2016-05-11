package cn.edu.jxnu.awesome_campus.support.utils.login;

import android.util.Log;
import android.widget.EditText;

import com.squareup.okhttp.Headers;

import cn.edu.jxnu.awesome_campus.api.JxnugoApi;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnugoLoginBean;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonCodeEntityCallback;

/**
 * Created by zpauly on 16-5-11.
 */
public class MarketLoginUtil {
    public static final String TAG = "MarketLoginUtil";

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
            NetManageUtil.getAuth(JxnugoApi.LoginUrl)
                    .addTag(TAG)
                    .addUserName(getUsername(usernameText))
                    .addPassword(getPassword(passwordText))
//                    .addParams("userName",getUsername(usernameText))
//                    .addParams("passWord",getPassword(passwordText))
                    .enqueue(new JsonCodeEntityCallback<JxnugoLoginBean>() {
                        @Override
                        public void onSuccess(JxnugoLoginBean entity, int responseCode, Headers headers) {
                            Log.d(TAG,"返回数据成功");
                            Log.d(TAG,"状态码"+responseCode);
                            if(!TextUtil.isNull(entity.getToken())){
                                Log.d("Token",entity.getToken());
                            }
                        }

                        @Override
                        public void onFailure(String error) {
                            Log.d(TAG,"登录失败"+error);
                        }
                    });
        }
    }

}
