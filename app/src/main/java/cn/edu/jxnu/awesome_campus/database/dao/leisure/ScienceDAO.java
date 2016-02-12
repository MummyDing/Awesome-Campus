package cn.edu.jxnu.awesome_campus.database.dao.leisure;

import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.api.ScienceApi;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.leisure.DailyModel;
import cn.edu.jxnu.awesome_campus.model.leisure.ScienceBean;
import cn.edu.jxnu.awesome_campus.model.leisure.ScienceModel;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;

/**
 * Created by MummyDing on 16-2-12.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ScienceDAO implements DAO<ScienceModel> {
    public final String TAG = "ScienceDAO";
    @Override
    public boolean cacheAll(List<ScienceModel> list) {
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

        NetManageUtil.get(ScienceApi.science_url)
                .addTag(TAG)
                .enqueue(new JsonEntityCallback<ScienceBean>() {
                    @Override
                    public void onFailure(IOException e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<ScienceModel>(EVENT.SCIENCE_REFRESH_FAILURE));
                            }
                        });
                    }

                    @Override
                    public void onSuccess(ScienceBean entity, Headers headers) {
                        final List<ScienceModel> list = Arrays.asList(entity.getResult());
                        cacheAll(list);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<ScienceModel>(EVENT.SCIENCE_REFRESH_SUCCESS,list));
                            }
                        });
                    }
                });

    }
}
