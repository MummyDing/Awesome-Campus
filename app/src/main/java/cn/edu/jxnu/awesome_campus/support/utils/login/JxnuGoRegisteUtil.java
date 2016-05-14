package cn.edu.jxnu.awesome_campus.support.utils.login;

import android.os.Handler;
import android.util.Log;
import android.widget.EditText;

import com.squareup.okhttp.Headers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoRegisterBean;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
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
        final PostJsonRequest request = new PostJsonRequest(JxnuGoApi.RegisterUrl);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                request.addJsonObject(bean)
                        .enqueue(new StringCodeCallback() {
                            @Override
                            public void onSuccess(String result, int responseCode, Headers headers) {
                                Log.i(TAG, "" + responseCode);
                                if (responseCode == 200) {
                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailure(String error) {
                                Log.i(TAG, "error");
                            }
                        });
            }
        });
    }
}
