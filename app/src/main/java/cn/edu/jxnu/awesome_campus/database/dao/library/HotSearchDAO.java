package cn.edu.jxnu.awesome_campus.database.dao.library;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.database.DatabaseHelper;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.database.table.library.HotSearchTable;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.library.HotSearchModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.libary.HotSearchParse;
import cn.edu.jxnu.awesome_campus.support.config.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;

/**
 * Created by KevinWu on 16-4-23.
 */
public class HotSearchDAO implements DAO<HotSearchModel> {
    public static final String TAG="HotSearchDAO";
    @Override
    public boolean cacheAll(List<HotSearchModel> list) {
        if (list == null || list.isEmpty()){
            return false;
        }

        clearCache();

        for (int i=0 ; i<list.size() ;i++){
            HotSearchModel model = list.get(i);
            ContentValues values = new ContentValues();
            values.put(HotSearchTable.TAG,model.getTag());
            DatabaseHelper.insert(HotSearchTable.NAME,values);
        }
        return true;
    }

    @Override
    public boolean clearCache() {
        DatabaseHelper.clearTable(HotSearchTable.NAME);
        return true;
    }

    @Override
    public void loadFromCache() {
        final Handler handler = new Handler(Looper.getMainLooper());
        Cursor cursor = DatabaseHelper.selectAll(HotSearchTable.NAME);

        final List<HotSearchModel> list = new ArrayList<>();

        while (cursor.moveToNext()){
            HotSearchModel model = new HotSearchModel(cursor.getString(HotSearchTable.ID_TAG));
            list.add(model);
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!list.isEmpty()){
                    //从缓存获取成功
                    EventBus.getDefault().post(new EventModel<HotSearchModel>(EVENT.HOT_SEARCH_LOAD_CACHE_SUCCESS,list));
                }else {
                    //从缓存获取失败
                    EventBus.getDefault().post(new EventModel<HotSearchModel>(EVENT.HOT_SEARCH_LOAD_CACHE_FAILURE));
                }
            }
        });
    }

    @Override
    public void loadFromNet() {
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
                                if (list != null&&!list.isEmpty()) {
                                    // 缓存数据
                                    cacheAll(list);
                                    //发送获取成功消息
                                    EventBus.getDefault().post(new EventModel<HotSearchModel>(EVENT.HOT_SEARCH_REFRESH_SUCCESS, list));
                                } else {
                                    //发送获取失败消息
                                    EventBus.getDefault().post(new EventModel<HotSearchModel>(EVENT.HOT_SEARCH_REFRESH_FAILURE));
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(String error) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<HotSearchModel>(EVENT.HOT_SEARCH_REFRESH_FAILURE));
                            }
                        });
                    }
                });
    }
}
