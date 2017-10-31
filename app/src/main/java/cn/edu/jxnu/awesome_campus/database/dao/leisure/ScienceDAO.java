package cn.edu.jxnu.awesome_campus.database.dao.leisure;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.api.ScienceApi;
import cn.edu.jxnu.awesome_campus.database.DatabaseHelper;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.database.table.leisure.ScienceTable;
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
    public static final String TAG = "ScienceDAO";
    @Override
    public boolean cacheAll(List<ScienceModel> list) {

        if (list == null || list.isEmpty()){
            return false;
        }

        clearCache();

        for (int i=0 ;i<list.size() ; i++){
            ScienceModel scienceModel = list.get(i);
            ContentValues values = new ContentValues();
            values.put(ScienceTable.REPLIES_COUNT,scienceModel.getReplies_count());
            values.put(ScienceTable.IMAGE_URL,scienceModel.getImage_info().getUrl());
            values.put(ScienceTable.URL,scienceModel.getUrl());
            values.put(ScienceTable.TITLE,scienceModel.getTitle());
            values.put(ScienceTable.SCIENCE_DETAILS,scienceModel.getScienceDetails());
            DatabaseHelper.insert(ScienceTable.NAME,values);
        }
        return true;
    }

    @Override
    public boolean clearCache() {
        DatabaseHelper.clearTable(ScienceTable.NAME);
        return true;
    }

    @Override
    public void loadFromCache() {
        final Handler handler = new Handler(Looper.getMainLooper());
        Cursor cursor = DatabaseHelper.selectAll(ScienceTable.NAME);

        final List<ScienceModel> list = new ArrayList<>();

        while (cursor.moveToNext()){
            ScienceModel model = new ScienceModel();
            model.setReplies_count(cursor.getInt(ScienceTable.ID_REPLIES_COUNT));
            model.getImage_info().setUrl(cursor.getString(ScienceTable.ID_IMAGE_URL));
            model.setUrl(cursor.getString(ScienceTable.ID_URL));
            model.setTitle(cursor.getString(ScienceTable.ID_TITLE));
            model.setScienceDetails(cursor.getString(ScienceTable.ID_SCIENCE_DETAILS));
            list.add(model);
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (list!=null&&!list.isEmpty()){
                    // 从缓存获取成功　发送消息
                    EventBus.getDefault().post(new EventModel<ScienceModel>(EVENT.SCIENCE_LOAD_CACHE_SUCCESS,list));
                }else {
                    // 从缓存获取失败
                    EventBus.getDefault().post(new EventModel<ScienceModel>(EVENT.SCIENCE_LOAD_CACHE_FAILURE));
                }
            }
        });
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
                    public void onSuccess(final ScienceBean entity, Headers headers) {
                       if(entity!=null){
                           final List<ScienceModel> list = entity.getResult();
                           cacheAll(list);
                           handler.post(new Runnable() {
                               @Override
                               public void run() {
                                   EventBus.getDefault().post(new EventModel<ScienceModel>(EVENT.SCIENCE_REFRESH_SUCCESS,list));
                               }
                           });
                       }else{
                           handler.post(new Runnable() {
                               @Override
                               public void run() {
                                   EventBus.getDefault().post(new EventModel<ScienceModel>(EVENT.SCIENCE_REFRESH_FAILURE));
                               }
                           });
                       }

                    }
                });

    }
}
