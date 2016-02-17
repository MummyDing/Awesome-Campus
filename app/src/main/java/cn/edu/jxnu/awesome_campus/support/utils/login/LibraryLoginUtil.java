package cn.edu.jxnu.awesome_campus.support.utils.login;

import android.util.Log;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.database.spkey.EducationStaticKey;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;

/**
 * Created by MummyDing on 16-2-17.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class LibraryLoginUtil {

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
    public static void onLogin(final EditText usernameET, EditText passwordET) {
        if (TextUtil.isNull(getUsername(usernameET)) || TextUtil.isNull(getPassword(passwordET))) {
            EventBus.getDefault().post(new EventModel<String>(EVENT.EDUCATION_LOGIN_FAILURE_NULL_INPUT));
            return;
        }

        // 这里请求网路
    }


    /**
     * 这个方法是根据当前cookie是否存在来判断当前是否处于登陆状态【查询本地sp即可】
     *
     * @return
     */

    public static boolean isLogin() {

        Log.d("执行到判断是否登录的方法","--");
       /* SPUtil sp = new SPUtil(InitApp.AppContext);
        // 这里改成Library的
        String cookie = sp.getStringSP(EducationStaticKey.SP_FILE_NAME, EducationStaticKey.BASE_COOKIE);
        if (TextUtil.isNull(cookie) == false) {
            Log.d("已登录","--");

            // 获取cookie
            return true;
        }
        Log.d("未登录","--");*/
        return false;
    }

    /**
     * 清除本地Cookie，主要应用场景:
     * 1. 注销登陆
     * 2. 当前cookie失效
     */
    public static void clearCookie() {
    }


}
