package cn.edu.jxnu.awesome_campus.database.dao.home;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.education.CourseScoreModel;
import cn.edu.jxnu.awesome_campus.model.home.CampusNewsModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.CampusNewsParse;
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
        final List<CampusNewsModel> resultList = new ArrayList<>();
        
        NetManageUtil.get(Urlconfig.CampusNews_YW_URL)
                .addTag(TAG)
                .enqueue(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Headers headers) {
                        Log.d("net exe","1111");
                        CampusNewsParse myParse=new CampusNewsParse(result);
                        resultList.addAll(myParse.getEndList());
                    }
                    @Override
                    public void onFailure(String error) {
                    }
                });

        NetManageUtil.get(Urlconfig.CampusNews_DT_URL)
                .addTag(TAG)
                .enqueue(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Headers headers) {
                        Log.d("net exe","2222");
                        CampusNewsParse myParse=new CampusNewsParse(result);
                        resultList.addAll(myParse.getEndList());
                    }
                    @Override
                    public void onFailure(String error) {
                    }
                });

        NetManageUtil.get(Urlconfig.CampusNews_MT_URL)
                .addTag(TAG)
                .enqueue(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Headers headers) {
                        Log.d("net exe","3333");
                        CampusNewsParse myParse=new CampusNewsParse(result);
                        resultList.addAll(myParse.getEndList());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (resultList.size()>0) {
                                    EventBus.getDefault().post(new EventModel<CampusNewsModel>(EVENT.CAMPUS_NEWS_REFRESH_SUCCESS, resultList));
                                    cacheAll(resultList);
                                } else {
                                    EventBus.getDefault().post(new EventModel<CampusNewsModel>(EVENT.CAMPUS_NEWS_REFRESH_FAILURE));
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(String error) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<CampusNewsModel>(EVENT.CAMPUS_NEWS_REFRESH_FAILURE));
                            }
                        });
                    }
                });


    }

}
