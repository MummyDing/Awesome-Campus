package cn.edu.jxnu.awesome_campus.database.dao.library;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.library.SelfStudySeatLeftModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.libary.SelfStudySeatLeftParse;
import cn.edu.jxnu.awesome_campus.support.config.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;

/**
 * Created by KevinWu on 16-4-24.
 */
public class SelfStudySeatLeftDAO implements DAO<SelfStudySeatLeftModel> {
    private static final String TAG="SelfStudySeatLeftDAO";

    private String cookie;

    public SelfStudySeatLeftDAO() {
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    @Override
    public boolean cacheAll(List<SelfStudySeatLeftModel> list) {
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
        Log.d(TAG,"联网拉去自习室数据");
        final Handler handler = new Handler(Looper.getMainLooper());
        if (TextUtil.isNull(cookie)) return;

        NetManageUtil.get(Urlconfig.SelfStudyRoom_Seat_Left_URL)
                .addHeader("Cookie", cookie)
                .addTag(TAG)
                .enqueue(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Headers headers) {
                        SelfStudySeatLeftParse parse=new SelfStudySeatLeftParse(result);
                        final List list = parse.getEndList();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (list != null && !list.isEmpty()) {
                                    //发送获取成功消息
                                    EventBus.getDefault().post(new EventModel<SelfStudySeatLeftModel>(EVENT.SELF_STUDY_SEATS_REFRESH_SUCCESS, list));
                                } else {
                                    //发送获取失败消息
                                    EventBus.getDefault().post(new EventModel<SelfStudySeatLeftModel>(EVENT.SELF_STUDY_SEATS_REFRESH_FAILURE));
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(String error) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<SelfStudySeatLeftModel>(EVENT.SELF_STUDY_SEATS_REFRESH_FAILURE));                            }
                        });
                    }
                });
    }
}
