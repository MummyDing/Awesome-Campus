package cn.edu.jxnu.awesome_campus.database.dao.leisure;

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
import cn.edu.jxnu.awesome_campus.database.table.leisure.FilmTable;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.leisure.FilmModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.leisure.JianshuListParse;
import cn.edu.jxnu.awesome_campus.support.config.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;

/**
 * Created by MummyDing on 16-2-17.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class FilmDAO implements DAO<FilmModel> {

    public static final String TAG = "FilmDAO";
    @Override
    public boolean cacheAll(List<FilmModel> list) {

        if (list == null || list.isEmpty()){
            return false;
        }

        clearCache();

        for (int i=0 ; i<list.size() ;i++){
            FilmModel filmModel = list.get(i);
            ContentValues values = new ContentValues();
            values.put(FilmTable.URL,filmModel.getUrl());
            values.put(FilmTable.TITLE,filmModel.getTitle());
            values.put(FilmTable.READING_COUNT,filmModel.getReadingCount());
            values.put(FilmTable.DETAIL,filmModel.getDetail());
            values.put(FilmTable.TOP_PIC,filmModel.getTopPic());
            DatabaseHelper.insert(FilmTable.NAME,values);
        }
        return true;
    }

    @Override
    public boolean clearCache() {
        DatabaseHelper.clearTable(FilmTable.NAME);
        return true;
    }

    @Override
    public void loadFromCache() {
        final Handler handler = new Handler(Looper.getMainLooper());
        Cursor cursor = DatabaseHelper.selectAll(FilmTable.NAME);

        final List<FilmModel> list = new ArrayList<>();

        while (cursor.moveToNext()){
            FilmModel model = new FilmModel();
            model.setUrl(cursor.getString(FilmTable.ID_URL));
            model.setTitle(cursor.getString(FilmTable.ID_TITLE));
            model.setReadingCount(cursor.getString(FilmTable.ID_READING_COUNT));
            model.setDetail(cursor.getString(FilmTable.ID_DETAIL));
            model.setTopPic(cursor.getString(FilmTable.ID_TOP_PIC));
            list.add(model);
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (list!=null&&!list.isEmpty()){
                    // 从缓存获取成功　发送消息
                    EventBus.getDefault().post(new EventModel<FilmModel>(EVENT.FILM_LOAD_CACHE_SUCCESS,list));
                }else {
                    // 从缓存获取失败
                    EventBus.getDefault().post(new EventModel<FilmModel>(EVENT.FILM_LOAD_CACHE_FAILURE));
                }
            }
        });
    }

    @Override
    public void loadFromNet() {
        final Handler handler = new Handler(Looper.getMainLooper());
        NetManageUtil.get(Urlconfig.JianShu_List_URL)
                .addTag(TAG).enqueue(new StringCallback() {
            @Override
            public void onSuccess(String result, Headers headers) {
                JianshuListParse myParse = new JianshuListParse(result);
                final List<FilmModel> list = myParse.getEndList();
                System.out.println("列表大小：" + list.size());
                //  for (int i = 0; i < list.size(); i++)
                //System.out.println("列表数据：" + list.get(i).getOneTwo().toString());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (list != null) {
                            cacheAll(list);
                            EventBus.getDefault().post(new EventModel<FilmModel>(EVENT.FILM_REFRESH_SUCCESS, list));
                        } else {
                            EventBus.getDefault().post(new EventModel<FilmModel>(EVENT.FILM_REFRESH_FAILURE));
                        }
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                Log.d("课程信息获取失败", error);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventModel<FilmModel>(EVENT.FILM_REFRESH_SUCCESS));

                    }
                });
            }
        });
    }
}
