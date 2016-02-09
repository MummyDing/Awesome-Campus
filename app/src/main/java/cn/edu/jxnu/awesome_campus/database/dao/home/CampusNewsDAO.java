package cn.edu.jxnu.awesome_campus.database.dao.home;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.education.CourseScoreModel;
import cn.edu.jxnu.awesome_campus.model.home.CampusNewsModel;
import cn.edu.jxnu.awesome_campus.support.htmlprase.CampusNewsPrase;
import cn.edu.jxnu.awesome_campus.support.urlconfig.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;

/**
 * Created by MummyDing on 16-2-2.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CampusNewsDAO implements DAO<CampusNewsModel> {
    public static final String TAG="CampusNewsDAO";
    @Override
    public boolean cacheAll(List<CampusNewsModel> list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache() {

    }

    @Override
    public void loadFromNet() {
        final Handler handler = new Handler(Looper.getMainLooper());
        NetManageUtil.get(Urlconfig.CampusNews_URL)
                .addTag(TAG)
                .enqueue(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Headers headers) {
                        CampusNewsPrase myPrase=new CampusNewsPrase(result);
                        final List list = myPrase.getEndList();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (list != null) {
                                    Log.d("news ","count"+list.size());
                                    EventBus.getDefault().post(new EventModel<CourseScoreModel>(EVENT.CAMPUS_NEWS_REFRESH_SUCCESS, list));
                                } else {
                                    EventBus.getDefault().post(new EventModel<CourseScoreModel>(EVENT.CAMPUS_NEWS_REFRESH_FAILURE));
                                }
                            }
                        });

                    }
                    @Override
                    public void onFailure(String error) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<CourseScoreModel>(EVENT.CAMPUS_NEWS_REFRESH_FAILURE));
                            }
                        });
                    }
                });

    }

}
