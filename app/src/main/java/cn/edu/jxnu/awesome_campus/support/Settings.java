package cn.edu.jxnu.awesome_campus.support;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.support.utils.common.PollingUtils;

/**
 * Created by MummyDing on 16-4-17.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class Settings {

    public static boolean needRecreate = false;

    public static boolean needUpdateAvatar = false;

    public static boolean autoRefresh = true;

    public static boolean isExitConfirm = true;

    public static int avatorID = 1;

    public static int swipeID = 0;


    public static boolean closeJobInfo=false;



    public static final String XML_NAME = "settings";

    public static final String LANGUAGE = "language";

    public static final String AVATAR = InitApp.AppContext.getString(R.string.id_avatar);

    public static final String  AUTO_REFRESH = "auto_refresh";

    public static final String EXIT_CONFIRM = "exit_confirm";

    public static final String CLEAR_CACHE = "clear_cache";

    public static final String SWIPE_BACK = "swipe_back";

    public static final String INTRO_VERSION = "intro_version";

    public static final String CLOSE_JOB_INFO="close_job_info";

    public static  String JXNUGO_AUTH_TOKEN="";

    private static Settings sInstance;

    private static SharedPreferences mPrefs;

    public static Settings getsInstance(){
        if (sInstance == null){
            sInstance = new Settings(InitApp.AppContext);
        }
        return sInstance;
    }

    private Settings(Context context){
        mPrefs = context.getSharedPreferences(XML_NAME,Context.MODE_PRIVATE);
    }

    public Settings putBoolean(String key,boolean value){
        mPrefs.edit().putBoolean(key,value).commit();
        return this;
    }

    public boolean getBoolean(String key, boolean def){
        return mPrefs.getBoolean(key,def);
    }

    public Settings putInt(String key, int value) {
        mPrefs.edit().putInt(key, value).commit();
        return this;
    }

    public int getInt(String key, int defValue) {
        return mPrefs.getInt(key, defValue);
    }

    public Settings putString(String key, String value) {
        mPrefs.edit().putString(key, value).commit();
        return this;
    }

    public String getString(String key, String defValue) {
        return mPrefs.getString(key, defValue);
    }


    public static String getJxnugoAuthToken() {
        return JXNUGO_AUTH_TOKEN;
    }

    public static void setJxnugoAuthToken() {
        try {
            InputStream in = InitApp.AppContext.getAssets().open("JxnuGoSecret");
            JXNUGO_AUTH_TOKEN = readTextFile(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static String readTextFile(InputStream inputStream) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
        }
        return outputStream.toString();
    }

}
