package cn.edu.jxnu.awesome_campus.database.dao.leisure;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.api.DailyApi;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.leisure.DailyBean;
import cn.edu.jxnu.awesome_campus.model.leisure.DailyModel;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;

/**
 * Created by MummyDing on 16-2-10.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class DailyDAO implements DAO<DailyModel>{
    public final String TAG = "DailyModel";

    @Override
    public boolean cacheAll(List<DailyModel> list) {
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
        NetManageUtil.get(DailyApi.daily_url)
                .addTag(TAG)
                .enqueue(new JsonEntityCallback<DailyBean>() {
                    @Override
                    public void onFailure(IOException e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<DailyModel>(EVENT.DAILY_REFRESH_FAILURE));
                            }
                        });
                    }

                    @Override
                    public void onSuccess(DailyBean entity, Headers headers) {
                        final List<DailyModel> list = Arrays.asList(entity.getStories());
                        cacheAll(list);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<DailyModel>(EVENT.DAILY_REFRESH_SUCCESS,list));
                            }
                        });
                    }
                });
    }
}
