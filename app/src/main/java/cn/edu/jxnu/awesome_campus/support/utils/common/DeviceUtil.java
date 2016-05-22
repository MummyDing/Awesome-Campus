package cn.edu.jxnu.awesome_campus.support.utils.common;

import android.content.Context;
import android.content.pm.PackageManager;

import cn.edu.jxnu.awesome_campus.InitApp;

/**
 * Created by KevinWu on 16-5-22.
 */
public class DeviceUtil {
    private static final String packName="cn.edu.jxnu.awesome_campus";

    /**
     * 获取应用版本名称
     * @return
     */
    public static String getVerName() {
        String verName = "";
        try {
            verName = InitApp.AppContext.getPackageManager().getPackageInfo(packName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return verName;
    }
    /**
     * 获取应用版本号
     * @return
     */
    public static  int getVerCode() {
        int versionCode = 0;
        try {
            versionCode = InitApp.AppContext.getPackageManager().getPackageInfo(packName,
                    0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return versionCode;
    }

    /**
     * 获取设备型号
     * @return
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    public static int getOSVerson(){
        return android.os.Build.VERSION.SDK_INT;
    }
}
