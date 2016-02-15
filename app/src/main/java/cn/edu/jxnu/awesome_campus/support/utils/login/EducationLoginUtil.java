package cn.edu.jxnu.awesome_campus.support.utils.login;

import android.support.design.widget.Snackbar;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;

/**
 * Created by MummyDing on 16-2-15.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class EducationLoginUtil {

    private static String username;
    private static String password;
    private static String cookie;

    private static String getUsername(EditText usernameET) {
        if(usernameET == null){
            throw new IllegalArgumentException("args cannot be null");
        }
        username = usernameET.getText().toString();
        if(TextUtil.isNull(username)){
            Snackbar.make(usernameET,"student number cannot be null !",Snackbar.LENGTH_SHORT).show();
            return null;
        }else if(username.length() != 10){
            Snackbar.make(usernameET,"wrong student number !",Snackbar.LENGTH_SHORT).show();
            return null;
        }
        return username;
    }

    private static String getPassword(EditText passwordET) {
        if(passwordET == null){
            throw new IllegalArgumentException("args cannot be null");
        }
        password = passwordET.getText().toString();
        if(TextUtil.isNull(password)){
            Snackbar.make(passwordET,"password cannot be null !",Snackbar.LENGTH_SHORT).show();
            return null;
        }
        return password;
    }

    /**
     * 这个就是放在登陆界面调用的
     * @param usernameET
     * @param passwordET
     */
    public static void onLogin(EditText usernameET,EditText passwordET){
        if(TextUtil.isNull(getUsername(usernameET)) || TextUtil.isNull(getPassword(passwordET))){
            EventBus.getDefault().post(new EventModel<String>(EVENT.LOGIN_FAILURE));
            return;
        }

        // 这里就根据 username password 请求cookie
        // 成功 失败 像上边一样发消息即可，只不过上边是在主线程，你这里网络请求不是在主线程，你需要像以前的DAO一样，
        // 用Handler获取主线程发送消息
    }

    /**
     *  这个方法是根据当前cookie是否存在来判断当前是否处于登陆状态【查询本地sp即可】
     * @return
     */

    public static boolean isLogin(){
        if( TextUtil.isNull(cookie) == false){
            return true;
        }

        // 这里查询本地cookie 了... 查询到了可以赋值给cookie变量，这样下次就不用查询了
        return false;
    }

    /**
     * 将cookie存储到sp中
     */
    public static void saveCookie(){

    }

    /**
     * 清除本地Cookie，主要应用场景:
     * 1. 注销登陆
     * 2. 当前cookie失效
     */
    public static void clearCookie(){

    }
}
