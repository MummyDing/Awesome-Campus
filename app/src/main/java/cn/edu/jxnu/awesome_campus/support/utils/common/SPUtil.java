package cn.edu.jxnu.awesome_campus.support.utils.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences工具
 * Created by KevinWu on 2016/2/7.
 */
public class SPUtil {
    private Context context;

    public SPUtil(Context context) {
        super();
        this.context = context;
    }

    /**
     * 初始化SharedPreferences对象
     *
     * @author KevinWu
     * create at 2016/2/7 14:26
     */
    private SharedPreferences initSP(String spName) {
        SharedPreferences sp = context.getSharedPreferences(spName, 0);
        return sp;
    }

    /**
     * 获取int型Sharedpreferences
     *
     * @author KevinWu
     * create at 2016/2/7 14:27
     */
    public int getIntSP(String spName, String spKey) {
        SharedPreferences sp = initSP(spName);
        int spGet = sp.getInt(spKey, 0);
        return spGet;
    }

    /**
     * 获取long型Sharedpreferences
     *
     * @author KevinWu
     * create at 2016/2/7 14:27
     */
    public long getLongSP(String spName, String spKey) {
        SharedPreferences sp = initSP(spName);
        long spGet = sp.getLong(spKey, 0);
        return spGet;
    }

    /**
     * 获取float型Sharedpreferences
     *
     * @author KevinWu
     * create at 2016/2/7 14:27
     */
    public float getFloatSP(String spName, String spKey) {
        SharedPreferences sp = initSP(spName);
        float spGet = sp.getFloat(spKey, 0);
        return spGet;
    }

    /**
     * 获取boolean型Sharedpreferences
     *
     * @author KevinWu
     * create at 2016/2/7 14:27
     */
    public boolean getBooleanSP(String spName, String spKey) {
        SharedPreferences sp = initSP(spName);
        boolean spGet = sp.getBoolean(spKey, false);
        return spGet;
    }

    /**
     * 获取String型SharedPreferences
     *
     * @author KevinWu
     * create at 2016/2/7 14:55
     */
    public String getStringSP(String spName, String spKey) {
        SharedPreferences sp = initSP(spName);
        String spGet = sp.getString(spKey, null);
        return spGet;
    }

    /**
     * 填充int型SharedPreferences
     *
     * @author KevinWu
     * create at 2016/2/7 14:59
     */
    public void putIntSP(String spName, String spKey, int value) {
        SharedPreferences sp = initSP(spName);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt(spKey, value);
        ed.commit();
    }

    /**
     * 填充float型SharedPreferences
     *
     * @author KevinWu
     * create at 2016/2/7 14:59
     */
    public void putFloatSP(String spName, String spKey, float value) {
        SharedPreferences sp = initSP(spName);
        SharedPreferences.Editor ed = sp.edit();
        ed.putFloat(spKey, value);
        ed.commit();
    }

    /**
     * 填充boolean型SharedPreferences
     *
     * @author KevinWu
     * create at 2016/2/7 14:59
     */
    public void putBooleanSP(String spName, String spKey, boolean value) {
        SharedPreferences sp = initSP(spName);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean(spKey, value);
        ed.commit();
    }

    /**
     * 填充String型SharedPreferences
     *
     * @author KevinWu
     * create at 2016/2/7 14:59
     */
    public void putStringSP(String spName, String spKey, String value) {
        SharedPreferences sp = initSP(spName);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(spKey, value);
        ed.commit();
    }

    /**
     * 填充long型SharedPreferences
     *
     * @author KevinWu
     * create at 2016/2/7 14:59
     */
    public void putLongSP(String spName, String spKey, long value) {
        SharedPreferences sp = initSP(spName);
        SharedPreferences.Editor ed = sp.edit();
        ed.putLong(spKey, value);
        ed.commit();
    }

    /**
    *清空sp文件
    *@author KevinWu
    *create at 2016/2/15 22:25
    */
    public void clearSP(String spName){
        SharedPreferences sp = initSP(spName);
        SharedPreferences.Editor ed = sp.edit();
        ed.clear();
        ed.commit();
    }

}
