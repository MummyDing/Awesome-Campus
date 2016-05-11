package cn.edu.jxnu.awesome_campus.database.dao.leisure;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.api.DailyApi;
import cn.edu.jxnu.awesome_campus.database.DatabaseHelper;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.database.table.leisure.DailyTable;
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
    public static final String TAG = "DailyModel";

    @Override
    public boolean cacheAll(List<DailyModel> list) {
        if (list == null || list.isEmpty()){
            return false;
        }
        clearCache();
        for (int i=0 ; i<list.size() ; i++){
            DailyModel dailyModel = list.get(i);
            ContentValues values = new ContentValues();
            values.put(DailyTable.TITLE,dailyModel.getTitle());
            values.put(DailyTable.ID,dailyModel.getId());
            values.put(DailyTable.BODY,dailyModel.getBody());
            values.put(DailyTable.LARGE_PIC,dailyModel.getLargePic());
            values.put(DailyTable.ITEM_PIC,dailyModel.getImages()[0]);
            DatabaseHelper.insert(DailyTable.NAME,values);
        }

        return true;
    }

    @Override
    public boolean clearCache() {
        DatabaseHelper.clearTable(DailyTable.NAME);
        return true;
    }

    @Override
    public void loadFromCache() {
        final Handler handler = new Handler(Looper.getMainLooper());
        Cursor cursor = DatabaseHelper.selectAll(DailyTable.NAME);
        final List<DailyModel> list = new ArrayList<>();

        while (cursor.moveToNext()){
            DailyModel model = new DailyModel();
            model.setTitle(cursor.getString(DailyTable.ID_TITLE));
            model.setId(cursor.getInt(DailyTable.ID_ID));
            model.setBody(cursor.getString(DailyTable.ID_BODY));
            model.setLargePic(cursor.getString(DailyTable.ID_LARGE_PIC));
            model.setImages(new String[]{cursor.getString(DailyTable.ID_ITEM_PIC)});
            list.add(model);
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (list!=null&&list.size() != 0){
                    // 从缓存获取成功 发送消息
                    EventBus.getDefault().post(new EventModel<DailyModel>(EVENT.DAILY_LOAD_CACHE_SUCCESS,list));
                }else{
                    //从缓存获取失败
                    EventBus.getDefault().post(new EventModel<DailyModel>(EVENT.DAILY_LOAD_CACHE_FAILURE));
                }
            }
        });
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
                    public void onSuccess(final DailyBean entity, Headers headers) {
                        if(entity!=null){
                            Log.d("date:",entity.getDate());
                            final List<DailyModel> list = Arrays.asList(entity.getStories());
                            cacheAll(list);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {

                                        EventBus.getDefault().post(new EventModel<DailyModel>(EVENT.DAILY_REFRESH_SUCCESS,list));

                                }
                            });
                        }else{
                            EventBus.getDefault().post(new EventModel<DailyModel>(EVENT.DAILY_REFRESH_FAILURE));
                        }

                    }
                });
    }
}
