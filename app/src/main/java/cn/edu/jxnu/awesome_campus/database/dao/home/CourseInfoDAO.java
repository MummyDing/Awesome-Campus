package cn.edu.jxnu.awesome_campus.database.dao.home;

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
import cn.edu.jxnu.awesome_campus.support.config.CourseTableRequestConfig;
import cn.edu.jxnu.awesome_campus.support.spkey.EducationStaticKey;
import cn.edu.jxnu.awesome_campus.database.table.home.CourseInfoTable;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.education.CourseScoreModel;
import cn.edu.jxnu.awesome_campus.model.home.CourseInfoModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.education.CourseInfoParse;
import cn.edu.jxnu.awesome_campus.support.config.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TimeUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;

/**
 * Created by MummyDing on 16-2-3.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseInfoDAO implements DAO<CourseInfoModel>{

    public static final String TAG="CourseInfoDAO";
    final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public boolean cacheAll(List<CourseInfoModel> list) {
        if (list == null || list.size() == 0){
            return false;
        }

        clearCache();

        for (int i=0 ;i <list.size(); i++){
            CourseInfoModel model = list.get(i);
            ContentValues values = new ContentValues();
            values.put(CourseInfoTable.COURSE_NAME,model.getCourseName());
            values.put(CourseInfoTable.COURSE_ID,model.getCourseID());
            values.put(CourseInfoTable.COURSE_TEACHER,model.getCourseTeacher());
            values.put(CourseInfoTable.COURSE_CLASS,model.getCourseClass());
            values.put(CourseInfoTable.CLASSMATE_LIST_LINK,model.getClassmateListLink());
            values.put(CourseInfoTable.CLASS_FORUM_LINK,model.getClassForumLink());
            DatabaseHelper.insert(CourseInfoTable.NAME,values);
        }
        return true;
    }

    @Override
    public boolean clearCache() {
        DatabaseHelper.clearTable(CourseInfoTable.NAME);
        return true;
    }

    @Override
    public void loadFromCache() {
        Cursor cursor = DatabaseHelper.selectAll(CourseInfoTable.NAME);
        final List<CourseInfoModel> list = new ArrayList<>();

        while (cursor.moveToNext()){
            CourseInfoModel model = new CourseInfoModel();
            model.setCourseName(cursor.getString(CourseInfoTable.ID_COURSE_NAME));
            model.setCourseID(cursor.getString(CourseInfoTable.ID_COURSE_ID));
            model.setCourseTeacher(cursor.getString(CourseInfoTable.ID_COURSE_TEACHER));
            model.setCourseClass(cursor.getString(CourseInfoTable.ID_COURSE_CLASS));
            model.setClassmateListLink(cursor.getString(CourseInfoTable.ID_CLASSMATE_LIST_LINK));
            model.setClassForumLink(cursor.getString(CourseInfoTable.ID_CLASS_FORUM_LINK));
            list.add(model);
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(list!=null&&list.size() != 0){
                    // 发送成功消息
                    EventBus.getDefault().post(new EventModel<CourseInfoModel>(EVENT.COURSE_INFO_LOAD_CACHE_SUCCESS,list));
                }else{
                    // 发送失败消息
                    EventBus.getDefault().post(new EventModel<CourseInfoModel>(EVENT.COURSE_INFO_LOAD_CACHE_FAILURE));
                }
            }
        });
    }

    @Override
    public void loadFromNet() {

        SPUtil spu = new SPUtil(InitApp.AppContext);
        String cookies = "_ga=GA1.3.609810117.1451115712; ASP.NET_SessionId=" +
                spu.getStringSP(EducationStaticKey.SP_FILE_NAME, EducationStaticKey.BASE_COOKIE) +
                ";JwOAUserSettingNew=" +
                spu.getStringSP(EducationStaticKey.SP_FILE_NAME, EducationStaticKey.SPECIAL_COOKIE);
//        NetManageUtil.get(Urlconfig.CourseTable_URL)
//                .addHeader("Cookie", cookies)
        NetManageUtil.post(Urlconfig.CourseTable_URL)
                .addHeader("Cookie", cookies)
                .addParams("__EVENTTARGET", CourseTableRequestConfig.__EVENTTARGET)
                .addParams("__EVENTARGUMENT", CourseTableRequestConfig.__EVENTARGUMENT)
                .addParams("__VIEWSTATE", CourseTableRequestConfig.__VIEWSTATE)
                .addParams("__EVENTVALIDATION", CourseTableRequestConfig.__EVENTVALIDATION)
                .addParams("_ctl1:ddlSterm",TimeUtil.getTerm())
//                .addParams("_ctl1:ddlSterm", "2015/3/1 0:00:00")
                .addParams("_ctl1:btnSearch","确定")
                .addTag(TAG).enqueue(new StringCallback() {
            @Override
            public void onSuccess(String result, Headers headers) {
                CourseInfoParse myParse = new CourseInfoParse(result);
                final List<CourseInfoModel> list = myParse.getEndList();
              //  for (int i = 0; i < list.size(); i++)
                    //System.out.println("列表数据：" + list.get(i).getOneTwo().toString());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (list != null) {
                            cacheAll(list);
                            EventBus.getDefault().post(new EventModel<CourseInfoModel>(EVENT.COURSE_INFO_REFRESH_SUCCESS, list));
                        } else {
                            EventBus.getDefault().post(new EventModel<CourseInfoModel>(EVENT.COURSE_INFO_REFRESH_FAILURE));
                        }
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventModel<CourseScoreModel>(EVENT.COURSE_INFO_REFRESH_FAILURE));

                    }
                });
            }
        });
    }
}
