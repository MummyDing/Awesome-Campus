package cn.edu.jxnu.awesome_campus.support.utils.login;

import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.widget.EditText;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.database.spkey.EducationStaticKey;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.education.EducationalSysLoginParse;
import cn.edu.jxnu.awesome_campus.support.urlconfig.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TermUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;

/**
 * Created by MummyDing on 16-2-15.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class EducationLoginUtil {
    public static final String TAG = "EducationLoginUtil";
    private static String username;
    private static String password;

    public static String getBaseCookie() {
        return baseCookie;
    }

    public static String getSpecialCookies() {
        return specialCookies;
    }

    private static String baseCookie;
    private static String specialCookies;


    private static String getUsername(EditText usernameET) {
        if (usernameET == null) {
            throw new IllegalArgumentException("args cannot be null");
        }
        username = usernameET.getText().toString();
        if (TextUtil.isNull(username)) {
            Snackbar.make(usernameET, "student number cannot be null !", Snackbar.LENGTH_SHORT).show();
            return null;
        } else if (username.length() != 10) {
            Snackbar.make(usernameET, "wrong student number !", Snackbar.LENGTH_SHORT).show();
            return null;
        }
        return username;
    }

    private static String getPassword(EditText passwordET) {
        if (passwordET == null) {
            throw new IllegalArgumentException("args cannot be null");
        }
        password = passwordET.getText().toString();
        if (TextUtil.isNull(password)) {
            Snackbar.make(passwordET, "password cannot be null !", Snackbar.LENGTH_SHORT).show();
            return null;
        }
        return password;
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
        } else {
            NetManageUtil.post(Urlconfig.Education_Login_URL)
                    .addTag(TAG)
                    .addParams("__EVENTTARGET", "")
                    .addParams("__EVENTARGUMENT", "")
                    .addParams("__LASTFOCUS", "")
                    .addParams("__VIEWSTATE", "/wEPDwUJNjk1MjA1MTY0D2QWAgIBD2QWBAIBDxYCHgdWaXNpYmxlZxYEZg8QZGQWAWZkAgEPEA8WBh4NRGF0YVRleHRGaWVsZAUM5Y2V5L2N5ZCN56ewHg5EYXRhVmFsdWVGaWVsZAUJ5Y2V5L2N5Y+3HgtfIURhdGFCb3VuZGdkEBU/CeS/neWNq+WkhAnotKLliqHlpIQS6LSi5pS/6YeR6J6N5a2m6ZmiEuaIkOS6uuaVmeiCsuWtpumZohLln47luILlu7rorr7lrabpmaIS5Yid562J5pWZ6IKy5a2m6ZmiDOS8oOaSreWtpumZoiHlvZPku6PlvaLmgIHmlofoibrlrabnoJTnqbbkuK3lv4MP5YWa5Yqe44CB5qCh5YqeCeaho+ahiOmmhhXlnLDnkIbkuI7njq/looPlrabpmaIb5a+55aSW6IGU57uc5LiO5o6l5b6F5Lit5b+DGOmrmOetieaVmeiCsueglOeptuS4reW/gxjlm73pmYXlkIjkvZzkuI7kuqTmtYHlpIQS5Zu96ZmF5pWZ6IKy5a2m6ZmiD+WQjuWLpOS/nemanOWkhBjljJblt6XlvIDlj5HnoJTnqbbkuK3lv4MS5YyW5a2m5YyW5bel5a2m6ZmiCeWfuuW7uuWkhBvorqHnrpfmnLrkv6Hmga/lt6XnqIvlrabpmaIq5rGf6KW/55yB5YWJ55S15a2Q5LiO6YCa5L+h6YeN54K55a6e6aqM5a6kD+aVmeW4iOaVmeiCsuWkhAnmlZnliqHlpIQM5pWZ6IKy5a2m6ZmiD+WGm+S6i+aVmeeglOWupBLnp5HmioDjgIHnpL7np5HlpIQS56eR5a2m5oqA5pyv5a2m6ZmiGOivvueoi+S4juaVmeWtpueglOeptuaJgBjnprvpgIDkvJHlt6XkvZzlip7lhazlrqQS55CG5YyW5rWL6K+V5Lit5b+DG+WOhuWPsuaWh+WMluS4juaXhea4uOWtpumZogznvo7mnK/lrabpmaIS5YWN6LS55biI6IyD55Sf6ZmiEuS6uuaJjeS6pOa1geS4reW/gwnkurrkuovlpIQM6L2v5Lu25a2m6ZmiCeWVhuWtpumZohvorr7lpIfkuI7lrp7pqozlrqTnrqHnkIblpIQS55Sf5ZG956eR5a2m5a2m6ZmiEuW4iOi1hOWfueiureS4reW/gxvmlbDlrabkuI7kv6Hmga/np5HlrablrabpmaIS57Sg6LSo5pWZ6IKy5Lit5b+DDOS9k+iCsuWtpumZognlm77kuabppoYP5aSW5Zu96K+t5a2m6ZmiHuWkluexjeS4k+WutueuoeeQhuacjeWKoeS4reW/gxLlpJbor63ogIPor5XkuK3lv4MP5paH5YyW56CU56m26ZmiCeaWh+WtpumZohvniannkIbkuI7pgJrkv6HnlLXlrZDlrabpmaIe546w5Luj5pWZ6IKy5oqA5pyv5bqU55So5Lit5b+DFeagoeWPi+W3peS9nOWKnuWFrOWupBXmoKHlm63nvZHnrqHnkIbkuK3lv4MM5b+D55CG5a2m6ZmiEuaWsOmXu+S/oeaBr+S4reW/gw/lrabmiqXmnYLlv5fnpL4P5a2m56eR5bu66K6+5aSECeWtpueUn+WkhAznoJTnqbbnlJ/pmaIS6Im65pyv56CU56m25Lit5b+DDOmfs+S5kOWtpumZog/mi5vnlJ/lsLHkuJrlpIQM5pS/5rOV5a2m6ZmiFT8IMTgwICAgICAIMTcwICAgICAINjgwMDAgICAINDUwICAgICAINjMwMDAgICAIODIwMDAgICAINjQwMDAgICAIMzgyICAgICAIMTMwICAgICAIMTA5ICAgICAINDgwMDAgICAIMTMyICAgICAIMzkwICAgICAIMTYwICAgICAINjkwMDAgICAIODcwMDAgICAIMzY1ICAgICAINjEwMDAgICAIMTQ0ICAgICAINjIwMDAgICAIMzgxICAgICAIMjUwICAgICAIMjQwMDAgICAINTAwMDAgICAIMzcwMDAgICAIMTQwICAgICAIODEwMDAgICAIMzI0ICAgICAIMTA0ICAgICAIMzIwICAgICAINTgwMDAgICAINjUwMDAgICAINTcwMDAgICAIMzMwICAgICAIMTUwICAgICAINjcwMDAgICAINTQwMDAgICAIMzYwICAgICAINjYwMDAgICAIMzEwICAgICAINTUwMDAgICAIMzgwMDAgICAINTYwMDAgICAIMjkwICAgICAINTIwMDAgICAIODkwMDAgICAIMzAwICAgICAIMzUwICAgICAINTEwMDAgICAINjAwMDAgICAIMzYxICAgICAIMTg5ICAgICAIMzA0ICAgICAINDkwMDAgICAIMTA2ICAgICAINDIwICAgICAIMTM2ICAgICAIMTEwICAgICAIMTkwICAgICAIMTQ2ICAgICAINTMwMDAgICAINDQwICAgICAINTkwMDAgICAUKwM/Z2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZGQCAw8PFgIfAGhkFgQCAQ8PFgIeCEltYWdlVXJsBUBNeUNvbnRyb2wvQWxsX1Bob3RvU2hvdy5hc3B4P1VzZXJOdW09MTMwODA5NTA3OCZVc2VyVHlwZT1TdHVkZW50ZGQCAw8PFgIeBFRleHQFnQHmrKLov47mgqjvvIzlkLTlkK/kuJw8YnI+PGEgdGFyZ2V0PV9ibGFuayBocmVmPU15Q29udHJvbC9TdHVkZW50X0luZm9yQ2hlY2suYXNweD48c3Ryb25nPjxmb250IGNvbG9yPXJlZCBzaXplPTM+5qCh5a+55Liq5Lq65L+h5oGvPC9mb250PjwvZm9udD48L3N0cm9uZz48L2E+ZGQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgEFClJlbWVuYmVyTWVL9DEQwq27B1OYvZ515c+Dw2RwqwMstx3xyKGTxh2WIA==")
                    .addParams("__EVENTVALIDATION", "/wEWSgLYl4WMBgLr6+/kCQK3yfbSBAKDspbeCQL21fViApC695MMAsjQmpEOAsjQpo4OAv3S2u0DAq/RroAOAv3S9t4DAqPW8tMDAv3S6tEDAoSwypgNAsjQtoIOArWVmJEHAr/R2u0DAsaw5o0NAo7QnpwOAsjQooMOAv3S3ugDAqPW5toDArfW7mMC/dL+0AMCvJDK9wsC/dLy0wMCw5aHjwMC6dGugA4C+dHq0QMC3NH61QMCntDm2gMCyNCqhQ4Co9b+0AMC8pHSiQwCvJDaiwwCjtCyhw4C3NHa7QMC/dLu3AMC3NHm2gMCjtC2gg4CyNCugA4C/dLm2gMC3NHq0QMCjtCigw4C/dLi3wMCjtC+hA4C3NHu3AMCntDa7QMC3NHi3wMC6dGenA4C3NHy0wMCo9be6AMCjtC6mQ4CjtCugA4C3NH+0AMC/dL61QMCw5bP/gICtZX4qQcC8pHaiwwCv9He6AMCqvCJ9QoCr9Gyhw4CqvCF/goCyNC+hA4CyNCenA4CqvC58QoC3NH23gMCr9GqhQ4C3NHe6AMC+euUqg4C2tqumwgC0sXgkQ8CuLeX+QECj8jxgAoP0m8Sj7LwLyeNyl7ka0HEgwkEhTIbhgvRBFELqH13qw==")
                    .addParams("rblUserType", "Student")
                    .addParams("ddlCollege", "180     ")
                    .addParams("StuNum", getUsername(usernameET))
                    .addParams("TeaNum", "")
                    .addParams("Password", getPassword(passwordET))
                    .addParams("login", "登录")
                    .enqueue(new StringCallback() {
                        @Override
                        public void onSuccess(String result, final Headers headers) {
                            EducationalSysLoginParse myParse = new EducationalSysLoginParse(result);
                            final List endList = myParse.getEndList();
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    if (endList.get(0).toString().equals(EducationalSysLoginParse.LOGIN_FAIL_NO_ID_STR)) {
                                        EventBus.getDefault().post(new EventModel<String>(EVENT.EDUCATION_LOGIN_FAILURE_NO_ID));
                                    } else if (endList.get(0).toString().equals(EducationalSysLoginParse.LOGIN_FAIL_PASSWORD_INCORRECT_STR)) {
                                        EventBus.getDefault().post(new EventModel<String>(EVENT.EDUCATION_LOGIN_FAILURE_PASSWORD_INCORRECT));
                                    } else {
                                        String userNum=getUsername(usernameET);
                                        String userName=endList.get(0).toString();
                                        String nowTerm= TermUtil.getNowTerm();
                                        String baseCookie=headers.get("ASP.NET_SessionId");
                                        String specialCookie=headers.get("JwOAUserSettingNew");
                                        saveToSP(userNum,userName,nowTerm,baseCookie,specialCookie);
                                        EventBus.getDefault().post(new EventModel<String>(EVENT.EDUCATION_LOGIN_SUCCESS));
                                    }
                                }
                            });
                        }

                        @Override
                        public void onFailure(String error) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<String>(EVENT.EDUCATION_LOGIN_FAILURE_NETWORK_ERROR));
                                }
                            });
                        }
                    });

        }

    }

    /**
     * 这个方法是根据当前cookie是否存在来判断当前是否处于登陆状态【查询本地sp即可】
     *
     * @return
     */

    public static boolean isLogin() {
        SPUtil sp=new SPUtil(InitApp.AppContext);
        String cookie=sp.getStringSP(EducationStaticKey.SP_FILE_NAME,EducationStaticKey.BASE_COOKIE);
        if (TextUtil.isNull(cookie) == false) {
            return true;
        }
        baseCookie=cookie;
        specialCookies=sp.getStringSP(EducationStaticKey.SP_FILE_NAME,EducationStaticKey.SPECIAL_COOKIE);
        return false;
    }

    /**
     * 保存获取到的信息
     *
     * @author KevinWu
     * create at 2016/2/15 17:22
     */
    private static void saveToSP(String userNum,String userName, String nowTerm, String baseCookie, String specialCookie) {
        SPUtil mysp=new SPUtil(InitApp.AppContext);
        EducationLoginUtil.baseCookie=baseCookie;
        EducationLoginUtil.specialCookies=specialCookie;
        mysp.putStringSP(EducationStaticKey.SP_FILE_NAME,EducationStaticKey.STUDENT_NUM,userNum);
        mysp.putStringSP(EducationStaticKey.SP_FILE_NAME,EducationStaticKey.STUDENT_NAME,userName);
        mysp.putStringSP(EducationStaticKey.SP_FILE_NAME,EducationStaticKey.NOW_TERM,nowTerm);
        mysp.putStringSP(EducationStaticKey.SP_FILE_NAME,EducationStaticKey.BASE_COOKIE,baseCookie);
        mysp.putStringSP(EducationStaticKey.SP_FILE_NAME,EducationStaticKey.SPECIAL_COOKIE,specialCookie);
    }

    /**
     * 清除本地Cookie，主要应用场景:
     * 1. 注销登陆
     * 2. 当前cookie失效
     */
    public static void clearCookie() {

    }
}
