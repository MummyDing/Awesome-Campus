package cn.edu.jxnu.awesome_campus.support.utils.login;

import android.widget.EditText;

import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;

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

    }

}
