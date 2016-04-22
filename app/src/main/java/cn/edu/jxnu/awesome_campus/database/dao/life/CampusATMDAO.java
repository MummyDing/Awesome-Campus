package cn.edu.jxnu.awesome_campus.database.dao.life;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.api.ATMApi;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.life.CampusATMBean;
import cn.edu.jxnu.awesome_campus.model.life.CampusATMModel;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;

/**
 * Created by MummyDing on 16-4-22.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CampusATMDAO implements DAO<CampusATMModel> {

    public static final String TAG = "CampusATMDAO";
    @Override
    public boolean cacheAll(List<CampusATMModel> list) {
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

        NetManageUtil.get(ATMApi.ATMUrl)
                .addTag(TAG)
                .enqueue(new JsonEntityCallback<CampusATMBean>() {
                    @Override
                    public void onFailure(IOException e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<CampusATMModel>(EVENT.CAMPUS_ATM_REFRESH_FAILURE));
                            }
                        });
                    }

                    @Override
                    public void onSuccess(CampusATMBean entity, Headers headers) {
                        final List<CampusATMModel> list = Arrays.asList(entity.getCampusATM());
                        cacheAll(list);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<CampusATMModel>(EVENT.CAMPUS_ATM_REFRESH_SUCCESS,list));
                            }
                        });
                    }
                });
    }
}
