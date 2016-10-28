package cn.edu.jxnu.awesome_campus.support.utils.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.api.VersoinApi;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.home.CampusNewsModel;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;

/**
 * Created by MummyDing on 16-4-23.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class SystemUtil {

    private static int versionCode = -1;

    private static String versionName = null;

    /**
     * App 版本号
     * @return
     */
    public static int getVersionCode() {
        if (versionCode != -1) return versionCode;
        return getPackageInfo().versionCode;
    }

    /**
     * 版本名
     * @return
     */
    public static String getVersionName() {
        if (TextUtil.isNull(versionName) == false) return versionName;
        return getPackageInfo().versionName;
    }



    public static boolean checkVersion(String versison){
        String regex="\\d+\\.\\d+\\.\\d+";
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(versison);
        return matcher.matches();
    }

    /**
     * 检查更新
     * @return
     */
    public static void tryCheckUpdate() {
        NetManageUtil.get(VersoinApi.versionUrl)
                .enqueue(new StringCallback() {
                    @Override
                    public void onSuccess(final String result, Headers headers) {
                        Log.d("目前版本",SystemUtil.getVersionName());
                        Log.d("远程版本",result);
                        Log.d("对比结果","--"+result.trim().compareTo(SystemUtil.getVersionName().toString()));




                        if (checkVersion(result)&&SystemUtil.getVersionName().toString().equals(result.trim())) {
                            // 最新
                            EventBus.getDefault().post(new EventModel(EVENT.VERSION_CHECK_ALREADY_LATEST));
                        } else if(checkVersion(result)&&result.trim().compareTo(SystemUtil.getVersionName().toString())>0){
                            // 要更新
                            EventBus.getDefault().post(new EventModel<String>(EVENT.VERSION_CHECK_NEED_UPDATE, result));
                        }else{
                            // 测试版本
                            EventBus.getDefault().post(new EventModel(EVENT.VERSION_CHECK_TEST_VERSION));
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        EventBus.getDefault().post(new EventModel(EVENT.VERSION_CHECK_FAILURE));
                    }
                });
    }

    /***
     * App包信息
     * @return
     */
    private static PackageInfo getPackageInfo() {
        PackageInfo pi = null;

        try {
            PackageManager pm = InitApp.AppContext.getPackageManager();
            pi = pm.getPackageInfo(InitApp.AppContext.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

}
