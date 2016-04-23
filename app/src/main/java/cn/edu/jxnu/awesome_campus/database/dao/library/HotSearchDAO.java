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
import cn.edu.jxnu.awesome_campus.model.library.HotSearchModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.libary.HotSearchParse;
import cn.edu.jxnu.awesome_campus.support.urlconfig.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;

/**
 * Created by KevinWu on 16-4-23.
 */
public class HotSearchDAO implements DAO<HotSearchModel> {
    private static final String TAG="HotSearchDAO";
    @Override
    public boolean cacheAll(List<HotSearchModel> list) {
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
        Log.d("HotSearch","联网拉取数据");
        final Handler handler = new Handler(Looper.getMainLooper());
        NetManageUtil.get(Urlconfig.Library_Book_HOT_Search)
                .addTag(TAG)
                .enqueue(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Headers headers) {
                        HotSearchParse parse=new HotSearchParse(result);
                        final List list = parse.getEndList();
                        Log.d(TAG,"标签数据获取完毕，结果列表大小为："+list.size());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (list != null) {
                                    // 缓存数据
                                    cacheAll(list);
                                    //发送获取成功消息
                                    EventBus.getDefault().post(new EventModel<HotSearchModel>(EVENT.BOOK_HOT_SEARCH_REFRESH_SUCCESS, list));
                                } else {
                                    //发送获取失败消息
                                    EventBus.getDefault().post(new EventModel<HotSearchModel>(EVENT.BOOK_HOT_SEARCH_REFRESH_FAILURE));
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.d(TAG,"标签数据获取失败，网络错误"+error);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<HotSearchModel>(EVENT.BOOK_HOT_SEARCH_REFRESH_FAILURE));
                            }
                        });
                    }
                });
    }
}
