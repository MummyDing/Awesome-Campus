package cn.edu.jxnu.awesome_campus.database.dao.life;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.api.ATMApi;
import cn.edu.jxnu.awesome_campus.database.DatabaseHelper;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.database.table.life.CampusATMTable;
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
        if (list == null || list.isEmpty()){
            return false;
        }

        clearCache();

        for (int i=0 ; i<list.size() ;i++){
            CampusATMModel atmModel = list.get(i);
            ContentValues values = new ContentValues();
            values.put(CampusATMTable.BANK_NAME,atmModel.getBankName());
            values.put(CampusATMTable.BANK_LOCATION,atmModel.getBankLocation());
            values.put(CampusATMTable.IMAGE_URL,atmModel.getImageUrl());
            DatabaseHelper.insert(CampusATMTable.NAME,values);
        }
        return true;
    }

    @Override
    public boolean clearCache() {
        DatabaseHelper.clearTable(CampusATMTable.NAME);
        return true;
    }

    @Override
    public void loadFromCache() {
        final Handler handler = new Handler(Looper.getMainLooper());
        Cursor cursor = DatabaseHelper.selectAll(CampusATMTable.NAME);

        final List<CampusATMModel> list = new ArrayList<>();

        while (cursor.moveToNext()){
            CampusATMModel model = new CampusATMModel();
            model.setBankName(cursor.getString(CampusATMTable.ID_BANK_NAME));
            model.setBankLocation(cursor.getString(CampusATMTable.ID_BANK_LOCATION));
            model.setImageUrl(cursor.getString(CampusATMTable.ID_IMAGE_URL));
            list.add(model);
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!list.isEmpty()){
                    //从缓存获取成功
                    EventBus.getDefault().post(new EventModel<CampusATMModel>(EVENT.CAMPUS_ATM_LOAD_CACHE_SUCCESS,list));
                }else {
                    //从缓存获取失败
                    EventBus.getDefault().post(new EventModel<CampusATMModel>(EVENT.CAMPUS_ATM_LOAD_CACHE_FAILURE));
                }
            }
        });
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
