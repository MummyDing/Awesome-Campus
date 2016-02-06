package cn.edu.jxnu.awesome_campus.database.dao.education;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.education.CourseScoreModel;
import cn.edu.jxnu.awesome_campus.support.htmlprase.CourseScorePrase;
import de.greenrobot.event.EventBus;
/**
 * Created by MummyDing on 16-2-2.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseScoreDAO implements DAO<CourseScoreModel> {
    @Override
    public boolean cacheAll(List<CourseScoreModel> list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache(List<CourseScoreModel> list) {

    }

    @Override
    public void loadFromNet(List<CourseScoreModel> list) {
        /**
         *
         *  原来Eventbus 还有数据传送的功能... 所以这个list可以不用 改成void loadFromeNet();都可以
         */
        String strFromNet="";//从网络中获取的数据
        //假设获取网络数据成功
        CourseScorePrase myPrase=new CourseScorePrase(strFromNet);
        list=myPrase.getEndList();
        if(list!=null){
            //发送获取成功消息
            EventBus.getDefault().post(new EventModel<CourseScoreModel>(EVENT.COURSE_SCORE_REFRESH_SUCCESS,list));
        }
        else{
            //发送获取失败消息
            EventBus.getDefault().post(new EventModel<CourseScoreModel>(EVENT.COURSE_SCORE_REFRESH_FAILURE));
        }

    }

}
