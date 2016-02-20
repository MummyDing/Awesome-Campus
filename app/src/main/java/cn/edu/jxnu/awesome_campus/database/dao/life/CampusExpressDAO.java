package cn.edu.jxnu.awesome_campus.database.dao.life;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.api.ExpressApi;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.life.CampusExpressBean;
import cn.edu.jxnu.awesome_campus.model.life.CampusExpressModel;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;

/**
 * Created by MummyDing on 16-2-20.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CampusExpressDAO implements DAO<CampusExpressModel> {

    public static final String TAG = "CampusExpressDAO";

    @Override
    public boolean cacheAll(List<CampusExpressModel> list) {
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

        NetManageUtil.get(ExpressApi.CampusExpressUrl)
                .addTag(TAG)
                .enqueue(new JsonEntityCallback<CampusExpressBean>() {
                    @Override
                    public void onFailure(IOException e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<CampusExpressModel>(EVENT.CAMPUS_EXPRESS_FAILURE));
                            }
                        });
                    }

                    @Override
                    public void onSuccess(CampusExpressBean entity, Headers headers) {
                        final List<CampusExpressModel> list = Arrays.asList(entity.getCampusExpress());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<CampusExpressModel>(EVENT.CAMPUS_EXPRESS_SUCCESS,list));
                            }
                        });

                    }
                });

    }
}
