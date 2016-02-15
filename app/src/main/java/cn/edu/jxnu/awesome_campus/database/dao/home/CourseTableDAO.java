package cn.edu.jxnu.awesome_campus.database.dao.home;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.database.spkey.EducationStaticKey;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.education.CourseScoreModel;
import cn.edu.jxnu.awesome_campus.model.home.CourseTableModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.education.CourseTableParse;
import cn.edu.jxnu.awesome_campus.support.urlconfig.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;

/**
 * Created by MummyDing on 16-2-3.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseTableDAO implements DAO<CourseTableModel> {
    public static final String TAG="CourseTableDAO";
    @Override
    public boolean cacheAll(List<CourseTableModel> list) {
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
        SPUtil spu = new SPUtil(InitApp.AppContext);
        String cookies = "_ga=GA1.3.609810117.1451115712; ASP.NET_SessionId=" +
                spu.getStringSP(EducationStaticKey.SP_FILE_NAME, EducationStaticKey.BASE_COOKIE) +
                ";JwOAUserSettingNew=" +
                spu.getStringSP(EducationStaticKey.SP_FILE_NAME, EducationStaticKey.SPECIAL_COOKIE);
        NetManageUtil.get(Urlconfig.CourseTable_URL)
                .addHeader("Cookie", cookies)
                .addTag(TAG).enqueue(new StringCallback() {
            @Override
            public void onSuccess(String result, Headers headers) {
                Log.d("result len: ",result.length()+" ");
                Log.d("result ",result.substring(10,100));
                Log.d("result ",result.substring(100,200));
                Log.d("result ",result.substring(200,300));
                Log.d("result ",result.substring(100,200));
               /* CourseTableParse myParse = new CourseTableParse(result);
                final List list = myParse.getEndList();
                System.out.println("列表大小：" + list.size());
                for (int i = 0; i < list.size(); i++)
                    System.out.println("列表数据：" + list.get(i).toString());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (list != null) {
                            cacheAll(list);
                            EventBus.getDefault().post(new EventModel<CourseTableModel>(EVENT.COURSE_TABLE_REFRESH_SUCCESS, list));
                        } else {
                            EventBus.getDefault().post(new EventModel<CourseTableModel>(EVENT.COURSE_TABLE_REFRESH_FAILURE));
                        }
                    }
                });*/
            }

            @Override
            public void onFailure(String error) {
                Log.d("课程表获取失败", error);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventModel<CourseScoreModel>(EVENT.COURSE_TABLE_REFRESH_FAILURE));

                    }
                });
            }
        });
    }
}
