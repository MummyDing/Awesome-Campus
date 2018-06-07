package cn.edu.jxnu.awesome_campus.support.utils.login;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.EditText;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.edu.jxnu.awesome_campus.AppConfig;
import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.model.config.AppConfigModel;
import cn.edu.jxnu.awesome_campus.support.spkey.EducationStaticKey;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.education.EducationalSysLoginParse;
import cn.edu.jxnu.awesome_campus.support.config.Urlconfig;
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
    private static String studentName;
    private static String studentID;

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
        } else {
            AppConfigModel appConfigModel = AppConfig.getConfig();
            NetManageUtil.post(Urlconfig.Education_Login_URL)
                    .addTag(TAG)
                    .addParams("__EVENTTARGET", "")
                    .addParams("__EVENTARGUMENT", "")
                    .addParams("__LASTFOCUS", "")
                    .addParams("__VIEWSTATE", appConfigModel.getEDU_LOGIN__VIEWSTATE())
                    .addParams("__EVENTVALIDATION", appConfigModel.getEDU_LOGIN__EVENTVALIDATION())
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
                                    if (endList == null || endList.isEmpty()){
                                        EventBus.getDefault().post(new EventModel<String>(EVENT.EDUCATION_LOGIN_SERVER_ERROR));
                                    }else if (endList.get(0).toString().equals(EducationalSysLoginParse.LOGIN_FAIL_NO_ID_STR)) {
                                        EventBus.getDefault().post(new EventModel<String>(EVENT.EDUCATION_LOGIN_FAILURE_NO_ID));
                                    } else if (endList.get(0).toString().equals(EducationalSysLoginParse.LOGIN_FAIL_PASSWORD_INCORRECT_STR)) {
                                        EventBus.getDefault().post(new EventModel<String>(EVENT.EDUCATION_LOGIN_FAILURE_PASSWORD_INCORRECT));
                                    } else {
                                        String userNum = getUsername(usernameET);
                                        String userName = endList.get(0).toString();
                                        String nowTerm = TermUtil.getNowTerm();
                                        String baseCookie=null,specialCookie=null;
                                        for (int i = 0; i < headers.size(); i++) {
                                            if(headers.name(i).equals("Set-Cookie")){
                                                baseCookie=cutBaseCookie(headers.value(i));
                                                specialCookie=cutSpecialCookie(headers.value(i+1));
                                                break;
                                            }
                                        }
                                        saveToSP(userNum, userName, nowTerm, baseCookie, specialCookie);
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
        Log.d("执行到判断是否登录的方法","--");
        SPUtil sp = new SPUtil(InitApp.AppContext);
        String cookie = sp.getStringSP(EducationStaticKey.SP_FILE_NAME, EducationStaticKey.BASE_COOKIE);
        if (TextUtil.isNull(cookie) == false) {
            Log.d("已登录","--");
            baseCookie = sp.getStringSP(EducationStaticKey.SP_FILE_NAME, EducationStaticKey.BASE_COOKIE);
            specialCookies = sp.getStringSP(EducationStaticKey.SP_FILE_NAME, EducationStaticKey.SPECIAL_COOKIE);
            studentID = sp.getStringSP(EducationStaticKey.SP_FILE_NAME,EducationStaticKey.STUDENT_NUM);
            studentName = sp.getStringSP(EducationStaticKey.SP_FILE_NAME,EducationStaticKey.STUDENT_NAME);
            return true;
        }
        Log.d("未登录","--");
        return false;
    }

    /**
     * 保存获取到的信息
     *
     * @author KevinWu
     * create at 2016/2/15 17:22
     */
    private static void saveToSP(String userNum, String userName, String nowTerm, String baseCookie, String specialCookie) {
        SPUtil mysp = new SPUtil(InitApp.AppContext);
        Log.d("取到的cookie:",""+baseCookie);
        EducationLoginUtil.baseCookie = baseCookie;
        EducationLoginUtil.specialCookies = specialCookie;
        mysp.putStringSP(EducationStaticKey.SP_FILE_NAME, EducationStaticKey.STUDENT_NUM, userNum);
        mysp.putStringSP(EducationStaticKey.SP_FILE_NAME, EducationStaticKey.STUDENT_NAME, userName);
        mysp.putStringSP(EducationStaticKey.SP_FILE_NAME, EducationStaticKey.NOW_TERM, nowTerm);
        mysp.putStringSP(EducationStaticKey.SP_FILE_NAME, EducationStaticKey.BASE_COOKIE, baseCookie);
        mysp.putStringSP(EducationStaticKey.SP_FILE_NAME, EducationStaticKey.SPECIAL_COOKIE, specialCookie);
    }

    /**
     * 清除本地Cookie，主要应用场景:
     * 1. 注销登陆
     * 2. 当前cookie失效
     */
    public static void clearCookie() {
        SPUtil sp=new SPUtil(InitApp.AppContext);
        sp.clearSP(EducationStaticKey.SP_FILE_NAME);
        studentName = null;
        studentID = null;
    }
    
    /**
    *分割baseCookie
    *@author KevinWu
    *create at 2016/2/15 20:57
    */
    private static String cutBaseCookie(String baseCookie){
        Log.d("待分割的cookie","-"+baseCookie);
        String first_cut[]=baseCookie.split("SessionId=");
        if(first_cut.length>1){
            String second_cut[]=first_cut[1].split("; path");
            if(second_cut.length>1){
                return second_cut[0];
            }
        }
        return null;
    }
    /**
    *分割specialCookie
    *@author KevinWu
    *create at 2016/2/15 21:16
    */
    private static String cutSpecialCookie(String specialCookie){
        String first_cut[]=specialCookie.split("SettingNew=");
        if(first_cut.length>1){
            String second_cut[]=first_cut[1].split("; expires=");
            if(second_cut.length>1){
                return second_cut[0];
            }
        }
        return null;
    }

    public static String getStudentName() {
        return studentName;
    }

    public static String getStudentID() {
        if (TextUtil.isNull(studentID)){
            SPUtil sp=new SPUtil(InitApp.AppContext);
            studentID = sp.getStringSP(EducationStaticKey.SP_FILE_NAME,EducationStaticKey.STUDENT_NUM);
        }
        return studentID;
    }

    public static String getAvatorUrl(){
        if(TextUtil.isNull(getStudentID())){
            return null;
        }
        return "http://jwc.jxnu.edu.cn/StudentPhoto/"+getStudentID()+".jpg";
    }
}
