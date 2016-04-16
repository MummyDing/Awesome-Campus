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

import cn.edu.jxnu.awesome_campus.api.ExpressApi;
import cn.edu.jxnu.awesome_campus.database.DatabaseHelper;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.database.table.life.CampusExpressTable;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.life.CampusExpressBean;
import cn.edu.jxnu.awesome_campus.model.life.CampusExpressModel;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;

/**
 * Created by MummyDing on 16-2-20.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CampusExpressDAO implements DAO<CampusExpressModel> {

    public static final String TAG = "CampusExpressDAO";

    @Override
    public boolean cacheAll(List<CampusExpressModel> list) {

        if (list == null || list.isEmpty()){
            return false;
        }

        clearCache();

        for (int i=0 ; i<list.size() ; i++){
            CampusExpressModel expressModel = list.get(i);
            ContentValues values = new ContentValues();
            values.put(CampusExpressTable.EXP_TEXT_NAME,expressModel.getExpTextName());
            values.put(CampusExpressTable.EXP_SPELL_NAME,expressModel.getExpSpellName());
            values.put(CampusExpressTable.EXP_TEL,expressModel.getExpTel());
            values.put(CampusExpressTable.EXP_LOC,expressModel.getExpLoc());
            values.put(CampusExpressTable.WORK_TIME,expressModel.getWorkTime());
            values.put(CampusExpressTable.ITEM_IMAGE,expressModel.getItemImage());
            values.put(CampusExpressTable.TOP_IMAGE,expressModel.getTopImage());
            values.put(CampusExpressTable.BODY,expressModel.getBody());
            DatabaseHelper.insert(CampusExpressTable.NAME,values);
        }

        return true;
    }

    @Override
    public boolean clearCache() {
        DatabaseHelper.clearTable(CampusExpressTable.NAME);
        return true;
    }

    @Override
    public void loadFromCache() {
        final Handler handler = new Handler(Looper.getMainLooper());
        Cursor cursor = DatabaseHelper.selectAll(CampusExpressTable.NAME);

        final List<CampusExpressModel> list = new ArrayList<>();

        while (cursor.moveToNext()){
            CampusExpressModel model = new CampusExpressModel();
            model.setExpTextName(cursor.getString(CampusExpressTable.ID_EXP_TEXT_NAME));
            model.setExpSpellName(cursor.getString(CampusExpressTable.ID_EXP_SPELL_NAME));
            model.setExpTel(cursor.getString(CampusExpressTable.ID_EXP_TEL));
            model.setExpLoc(cursor.getString(CampusExpressTable.ID_EXP_LOC));
            model.setWorkTime(cursor.getString(CampusExpressTable.ID_WORK_TIME));
            model.setItemImage(cursor.getString(CampusExpressTable.ID_ITEM_IMAGE));
            model.setTopImage(cursor.getString(CampusExpressTable.ID_TOP_IMAGE));
            model.setBody(cursor.getString(CampusExpressTable.ID_BODY));
            list.add(model);
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!list.isEmpty()){
                    //从缓存获取成功　发送消息
                    EventBus.getDefault().post(new EventModel<CampusExpressModel>(EVENT.CAMPUS_EXPRESS_LOAD_CACHE_SUCCESS,list));
                }else {
                    // 从缓存获取失败
                    EventBus.getDefault().post(new EventModel<CampusExpressModel>(EVENT.CAMPUS_EXPRESS_LOAD_CACHE_FAILURE));
                }
            }
        });
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
                        cacheAll(list);
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
