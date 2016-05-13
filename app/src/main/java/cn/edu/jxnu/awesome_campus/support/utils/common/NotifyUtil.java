package cn.edu.jxnu.awesome_campus.support.utils.common;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.about.NotifyDAO;
import cn.edu.jxnu.awesome_campus.model.about.NotifyModel;
import cn.edu.jxnu.awesome_campus.support.Settings;

/**
 * Created by MummyDing on 16-4-30.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class NotifyUtil {

    public static final String NotifyVersion = "notifyVersion";

    public static int hasUnread(List<NotifyModel> modelList){
        if (modelList == null || modelList.isEmpty()) return 0;
        int i=0;
        for (NotifyModel model:modelList){
            if (model.getReaded() == 0){
               i++;
            }
        }
        return i;
    }

    public static int hasUnread(){
        NotifyDAO dao = new NotifyDAO();
        return hasUnread(dao.getCache());
    }

    public static String getNotifyVersion(){
       return Settings.getsInstance().getString(NotifyVersion,"0");
    }

    public static void updateNotifyVersion(String newVersion){
        Settings.getsInstance().putString(NotifyVersion,newVersion);
    }

    public static boolean checkVersion(String version){
        return getNotifyVersion().equals(version);
    }
}
