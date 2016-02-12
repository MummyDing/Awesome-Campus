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

    private int count = 1;
    private Handler handler = new Handler(Looper.getMainLooper());
    @Override
    public void loadFromNet() {

        final List<CampusNewsModel> resultList = new ArrayList<CampusNewsModel>();
        
        NetManageUtil.get(Urlconfig.CampusNews_YW_URL)
                .addTag(TAG)
                .enqueue(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Headers headers) {
                        CampusNewsParse myParse=new CampusNewsParse(result);
                        resultList.addAll(myParse.getEndList());
                        if(count == 3 && resultList.size()>0){
                            sendSuccess(resultList);
                        }
                        count++;
                    }
                    @Override
                    public void onFailure(String error) {
                        if(count == 3 && resultList.size() == 0){
                            sendFailure();
                        }else if(count == 3){
                            sendSuccess(resultList);
                        }
                        count++;
                    }
                });

        NetManageUtil.get(Urlconfig.CampusNews_DT_URL)
                .addTag(TAG)
                .enqueue(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Headers headers) {
                        CampusNewsParse myParse=new CampusNewsParse(result);
                        resultList.addAll(myParse.getEndList());
                        if(count == 3 && resultList.size()>0){
                            sendSuccess(resultList);
                        }
                        count++;
                    }
                    @Override
                    public void onFailure(String error) {
                        if(count == 3 && resultList.size() == 0){
                            sendFailure();
                        }else if(count == 3){
                            sendSuccess(resultList);
                        }
                        count++;
                    }
                });

        NetManageUtil.get(Urlconfig.CampusNews_MT_URL)
                .addTag(TAG)
                .enqueue(new StringCallback() {
                    @Override
                    public  void onSuccess(String result, Headers headers) {
                        CampusNewsParse myParse=new CampusNewsParse(result);
                        resultList.addAll(myParse.getEndList());
                        if(count == 3 && resultList.size()>0){
                            sendSuccess(resultList);
                        }
                        count++;
                    }

                    @Override
                    public  void onFailure(String error) {
                        if(count == 3 && resultList.size() == 0){
                            sendFailure();
                        }else if(count == 3){
                            sendSuccess(resultList);
                        }
                        count++;
                    }
                });
    }

    private void sendSuccess(final List<CampusNewsModel> result){
        handler.post(new Runnable() {
            @Override
            public void run() {
                count = 1;
                EventBus.getDefault().post(new EventModel<CampusNewsModel>(EVENT.CAMPUS_NEWS_REFRESH_SUCCESS, result));
            }
        });
    }

    private void sendFailure(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                count = 1;
                EventBus.getDefault().post(new EventModel<CampusNewsModel>(EVENT.CAMPUS_NEWS_REFRESH_FAILURE));
            }
        });
    }

}
