package cn.edu.jxnu.awesome_campus.database.dao.leisure;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.leisure.FilmModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.leisure.JianshuListParse;
import cn.edu.jxnu.awesome_campus.support.urlconfig.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;

/**
 * Created by MummyDing on 16-2-17.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class FilmDAO implements DAO<FilmModel> {

    public static final String TAG = "FilmDAO";
    @Override
    public boolean cacheAll(List<FilmModel> list) {
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
        NetManageUtil.get(Urlconfig.JianShu_List_URL)
                .addTag(TAG).enqueue(new StringCallback() {
            @Override
            public void onSuccess(String result, Headers headers) {
                JianshuListParse myParse = new JianshuListParse(result);
                final List<FilmModel> list = myParse.getEndList();
                System.out.println("列表大小：" + list.size());
                //  for (int i = 0; i < list.size(); i++)
                //System.out.println("列表数据：" + list.get(i).getOneTwo().toString());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (list != null) {
                            cacheAll(list);
                            EventBus.getDefault().post(new EventModel<FilmModel>(EVENT.FILM_REFRESH_SUCCESS, list));
                        } else {
                            EventBus.getDefault().post(new EventModel<FilmModel>(EVENT.FILM_REFRESH_SUCCESS));
                        }
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                Log.d("课程信息获取失败", error);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventModel<FilmModel>(EVENT.FILM_REFRESH_SUCCESS));

                    }
                });
            }
        });
    }
}
