package cn.edu.jxnu.awesome_campus.support.utils.login;

import android.support.design.widget.Snackbar;
import android.widget.EditText;

import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;

/**
 * Created by MummyDing on 16-2-15.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class EducationLoginUtil {

    private String username;
    private String password;
    private String cookie;

    public String getUsername(EditText usernameET) {
        if(usernameET == null){
            throw new IllegalArgumentException("args cannot be null");
        }
        String str = usernameET.getText().toString();
        if(TextUtil.isNull(str)){
            Snackbar.make(usernameET,"student number cannot be null !",Snackbar.LENGTH_SHORT).show();
            return null;
        }else if(str.length() != 10){
            Snackbar.make(usernameET,"wrong student number !",Snackbar.LENGTH_SHORT).show();
            return null;
        }
        return str;
    }

    public String getPassword(EditText passwordET) {
        if(passwordET == null){
            throw new IllegalArgumentException("args cannot be null");
        }
        String str = passwordET.getText().toString();
        if(TextUtil.isNull(str)){
            Snackbar.make(passwordET,"password cannot be null !",Snackbar.LENGTH_SHORT).show();
            return null;
        }
        return str;
    }

    /**
     * 在这里获取cookie  先查询sp中是否有，没有就登陆
     * @return
     */
    public String getCookie() {
        return cookie;
    }

    public void saveCookie(){

    }
}
