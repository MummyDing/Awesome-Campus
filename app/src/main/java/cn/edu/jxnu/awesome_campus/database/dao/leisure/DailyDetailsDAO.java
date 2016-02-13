package cn.edu.jxnu.awesome_campus.database.dao.leisure;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.leisure.DailyDetailsBean;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;

/**
 * Created by MummyDing on 16-2-10.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class DailyDetailsDAO implements DAO<DailyDetailsBean> {

    private String url;
    @Override
    public boolean cacheAll(List<DailyDetailsBean> list) {
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
        NetManageUtil.post(url).enqueue(new JsonEntityCallback<DailyDetailsBean>() {
            @Override
            public void onFailure(IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventModel<DailyDetailsBean>(EVENT.DAILY_DETAIL_FAILURE));
                    }
                });
            }

            @Override
            public void onSuccess(final DailyDetailsBean entity, Headers headers) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(entity == null){
                            EventBus.getDefault().post(new EventModel<DailyDetailsBean>(EVENT.DAILY_DETAIL_FAILURE));
                        }else {
                            EventBus.getDefault().post(new EventModel<DailyDetailsBean>(EVENT.DAILY_DETAIL_SUCCESS,entity));
                        }
                    }
                });
            }
        });
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
