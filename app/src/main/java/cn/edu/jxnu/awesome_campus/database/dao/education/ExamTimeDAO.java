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
import cn.edu.jxnu.awesome_campus.database.table.education.ExamTimeTable;
import cn.edu.jxnu.awesome_campus.support.spkey.EducationStaticKey;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.education.CourseScoreModel;
import cn.edu.jxnu.awesome_campus.model.education.ExamTimeModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.education.ExamTimeParse;
import cn.edu.jxnu.awesome_campus.support.config.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;

/**
 * Created by MummyDing on 16-2-2.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ExamTimeDAO implements DAO<ExamTimeModel> {
    public static final String TAG="ExamTimeDAO";
    @Override
    public boolean cacheAll(List<ExamTimeModel> list) {

        if (list == null || list.isEmpty()){
            return false;
        }
        clearCache();
        for (int i=0 ; i<list.size() ; i++){
            ExamTimeModel model = list.get(i);
            ContentValues values = new ContentValues();
            values.put(ExamTimeTable.COURSE_ID,model.getCourseID());
            values.put(ExamTimeTable.COURSE_NAME,model.getCourseName());
            values.put(ExamTimeTable.EXAM_TIME,model.getExamTime());
            values.put(ExamTimeTable.EXAM_ROOM,model.getExamRoom());
            values.put(ExamTimeTable.EXAM_SEAT,model.getExamSeat());
            values.put(ExamTimeTable.REMARK,model.getRemark());
            DatabaseHelper.insert(ExamTimeTable.NAME,values);
        }
        return true;
    }

    @Override
    public boolean clearCache() {
        DatabaseHelper.clearTable(ExamTimeTable.NAME);
        return true;
    }

    @Override
    public void loadFromCache() {
        final Handler handler = new Handler(Looper.getMainLooper());
        Cursor cursor = DatabaseHelper.selectAll(ExamTimeTable.NAME);

        final List<ExamTimeModel> list = new ArrayList<>();

        while (cursor.moveToNext()){
            ExamTimeModel model = new ExamTimeModel();
            model.setCourseID(cursor.getString(ExamTimeTable.ID_COURSE_ID));
            model.setCourseName(cursor.getString(ExamTimeTable.ID_COURSE_NAME));
            model.setExamTime(cursor.getString(ExamTimeTable.ID_EXAM_TIME));
            model.setExamRoom(cursor.getString(ExamTimeTable.ID_EXAM_ROOM));
            model.setExamSeat(cursor.getString(ExamTimeTable.ID_EXAM_SEAT));
            model.setRemark(cursor.getString(ExamTimeTable.ID_REMARK));
            list.add(model);
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!list.isEmpty()){
                    // 从缓存获取成功
                    EventBus.getDefault().post(new EventModel<ExamTimeModel>(EVENT.EXAM_TIME_LOAD_CACHE_SUCCESS,list));
                }else {
                    //从缓存获取失败
                    EventBus.getDefault().post(new EventModel<ExamTimeModel>(EVENT.EXAM_TIME_LOAD_CACHE_FAILURE));
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
        NetManageUtil.get(Urlconfig.ExamTime_URL)
                .addHeader("Cookie", cookies)
                .addTag(TAG).enqueue(new StringCallback() {
            @Override
            public void onSuccess(String result, Headers headers) {
                ExamTimeParse myPrase = new ExamTimeParse(result);
                final List list = myPrase.getEndList();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (list != null) {
                            // 缓存数据
                            cacheAll(list);
                            //发送获取成功消息
                            EventBus.getDefault().post(new EventModel<CourseScoreModel>(EVENT.EXAM_TIME_REFRESH_SUCCESS, list));
                        } else {
                            //发送获取失败消息
                            EventBus.getDefault().post(new EventModel<CourseScoreModel>(EVENT.EXAM_TIME_REFRESH_FAILURE));
                        }
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventModel<CourseScoreModel>(EVENT.EXAM_TIME_REFRESH_FAILURE));
                    }
                });
              }
        });

    }

}
