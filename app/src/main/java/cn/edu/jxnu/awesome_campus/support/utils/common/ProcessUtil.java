package cn.edu.jxnu.awesome_campus.support.utils.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by KevinWu on 16-3-7.
 */
public class ProcessUtil {
    public static String getProcessName(Context context, int pid){
        ActivityManager am=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningsList=am.getRunningAppProcesses();
        if(runningsList==null){
            return null;
        }
        for(int i=0;i<runningsList.size();i++){
            if(runningsList.get(i).pid==pid){
                return runningsList.get(i).processName;
            }
        }

        return null;
    }
}
