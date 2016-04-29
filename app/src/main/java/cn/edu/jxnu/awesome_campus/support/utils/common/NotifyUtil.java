package cn.edu.jxnu.awesome_campus.support.utils.common;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.about.NotifyDAO;
import cn.edu.jxnu.awesome_campus.model.about.NotifyModel;

/**
 * Created by MummyDing on 16-4-30.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class NotifyUtil {
    public static boolean hasUnread(List<NotifyModel> modelList){
mei        if (modelList == null || modelList.isEmpty()) return false;
        for (NotifyModel model:modelList){
            if (!model.isReaded()){
                return true;
            }
        }
        return false;
    }

    public static boolean hasUnread(){
        NotifyDAO dao = new NotifyDAO();
        return hasUnread(dao.getCache());
    }
}
