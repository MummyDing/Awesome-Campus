package cn.edu.jxnu.awesome_campus.database.dao.education;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.database.DatabaseHelper;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.database.table.education.CourseScoreTable;
import cn.edu.jxnu.awesome_campus.support.spkey.EducationStaticKey;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.education.CourseScoreModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.education.CourseScoreParse;
import cn.edu.jxnu.awesome_campus.support.config.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;

/**
 * Created by MummyDing on 16-2-2.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseScoreDAO implements DAO<CourseScoreModel> {
    public static final String TAG = "CourseScoreDAO";

    @Override
    public boolean cacheAll(List<CourseScoreModel> list) {

        if (list == null || list.isEmpty()){
            return false;
        }
        clearCache();

        for (int i=0 ; i<list.size() ;i++){
            CourseScoreModel scoreModel = list.get(i);
            ContentValues values = new ContentValues();
            values.put(CourseScoreTable.TERM,scoreModel.getTerm());
            values.put(CourseScoreTable.COURSE_ID,scoreModel.getCourseID());
            values.put(CourseScoreTable.COURSE_NAME,scoreModel.getCourseName());
            values.put(CourseScoreTable.COURSE_CREDIT,scoreModel.getCourseCredit());
            values.put(CourseScoreTable.COURSE_SCORE,scoreModel.getCourseScore());
            values.put(CourseScoreTable.AGAIN_SCORE,scoreModel.getAgainScore());
            values.put(CourseScoreTable.STANDARD_SCORE,scoreModel.getStandardScore());
            DatabaseHelper.insert(CourseScoreTable.NAME,values);

        }
        return true;
    }

    @Override
    public boolean clearCache() {
        DatabaseHelper.clearTable(CourseScoreTable.NAME);
        return true;
    }

    @Override
    public void loadFromCache() {
        final Handler handler = new Handler(Looper.getMainLooper());
        Cursor cursor = DatabaseHelper.selectAll(CourseScoreTable.NAME);
        final List<CourseScoreModel> list = new ArrayList<>();

        while (cursor.moveToNext()){
            CourseScoreModel model = new CourseScoreModel();
            model.setTerm(cursor.getString(CourseScoreTable.ID_TERM));
            model.setCourseID(cursor.getString(CourseScoreTable.ID_COURSE_ID));
            model.setCourseName(cursor.getString(CourseScoreTable.ID_COURSE_NAME));
            model.setCourseCredit(cursor.getString(CourseScoreTable.ID_COURSE_CREDIT));
            model.setCourseScore(cursor.getString(CourseScoreTable.ID_COURSE_SCORE));
            model.setAgainScore(cursor.getString(CourseScoreTable.ID_AGAIN_SCORE));
            model.setStandardScore(cursor.getString(CourseScoreTable.ID_STANDARD_SCORE));
            list.add(model);
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!list.isEmpty()){
                    // 从缓存中获取成功　发送消息
                    EventBus.getDefault().post(new EventModel<CourseScoreModel>(EVENT.COURSE_SCORE_LOAD_CACHE_SUCCESS,list));
                }else {
                    // 从缓存获取失败
                    EventBus.getDefault().post(new EventModel<CourseScoreModel>(EVENT.COURSE_SCORE_LOAD_CACHE_FAILURE));
                }
            }
        });
    }

    @Override
    public void loadFromNet() {
        final Handler handler = new Handler(Looper.getMainLooper());
        SPUtil spu = new SPUtil(InitApp.AppContext);
        String cookies = "ASP.NET_SessionId=" +
                spu.getStringSP(EducationStaticKey.SP_FILE_NAME, EducationStaticKey.BASE_COOKIE) +
                ";JwOAUserSettingNew=" +
                spu.getStringSP(EducationStaticKey.SP_FILE_NAME, EducationStaticKey.SPECIAL_COOKIE);
        NetManageUtil.get(Urlconfig.CourseScore_URL)
                .addHeader("Cookie", cookies)
                .addTag(TAG).enqueue(new StringCallback() {
            @Override
            public void onSuccess(String result, Headers headers) {
                CourseScoreParse myParse = new CourseScoreParse(result);
                final List list = myParse.getEndList();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (list != null && !list.isEmpty()) {
                            // 缓存数据
                            cacheAll(list);
                            //发送获取成功消息
                            EventBus.getDefault().post(new EventModel<CourseScoreModel>(EVENT.COURSE_SCORE_REFRESH_SUCCESS, list));
                        } else {
                            //发送获取失败消息
                            EventBus.getDefault().post(new EventModel<CourseScoreModel>(EVENT.COURSE_SCORE_REFRESH_FAILURE));
                        }
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventModel<CourseScoreModel>(EVENT.COURSE_SCORE_REFRESH_FAILURE));

                    }
                });
            }
        });


    }

}
