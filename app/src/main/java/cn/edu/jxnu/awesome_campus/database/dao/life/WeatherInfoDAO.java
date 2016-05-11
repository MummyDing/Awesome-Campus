package cn.edu.jxnu.awesome_campus.database.dao.life;

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

import cn.edu.jxnu.awesome_campus.api.WeatherApi;
import cn.edu.jxnu.awesome_campus.database.DatabaseHelper;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.database.table.life.WeatherInfoTable;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.life.WeatherInfoBean;
import cn.edu.jxnu.awesome_campus.model.life.WeatherInfoModel;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;

/**
 * Created by MummyDing on 16-2-2.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class WeatherInfoDAO implements DAO<WeatherInfoModel> {

    public static final String TAG = "WeatherInfoDAO";
    @Override
    public boolean cacheAll(List<WeatherInfoModel> list) {

        if (list == null || list.isEmpty()){
            return false;
        }

        clearCache();


        for (int i=0 ; i<list.size() ; i++) {
            WeatherInfoModel model = list.get(i);
            ContentValues values = new ContentValues();
            values.put(WeatherInfoTable.DATE,model.getDate());
            values.put(WeatherInfoTable.DAY_ID,model.getInfo().getDay()[0]);
            values.put(WeatherInfoTable.DAY_WEA,model.getInfo().getDay()[1]);
            values.put(WeatherInfoTable.DAY_TMP,model.getInfo().getDay()[2]);
            values.put(WeatherInfoTable.DAY_WIND_DIR,model.getInfo().getDay()[3]);
            values.put(WeatherInfoTable.DAY_WIND_POWER,model.getInfo().getDay()[4]);
            values.put(WeatherInfoTable.DAY_TIME,model.getInfo().getDay()[5]);

            values.put(WeatherInfoTable.NIGHT_ID,model.getInfo().getNight()[0]);
            values.put(WeatherInfoTable.NIGHT_WEA,model.getInfo().getNight()[1]);
            values.put(WeatherInfoTable.NIGHT_TMP,model.getInfo().getNight()[2]);
            values.put(WeatherInfoTable.NIGHT_WIND_DIR,model.getInfo().getNight()[3]);
            values.put(WeatherInfoTable.NIGHT_WIND_POWER,model.getInfo().getNight()[4]);
            values.put(WeatherInfoTable.NIGHT_TIME,model.getInfo().getNight()[5]);

            DatabaseHelper.insert(WeatherInfoTable.NAME,values);

        }

        return true;
    }

    @Override
    public boolean clearCache() {
        DatabaseHelper.clearTable(WeatherInfoTable.NAME);
        return true;
    }

    @Override
    public void loadFromCache() {
        final Handler handler = new Handler(Looper.getMainLooper());
        Cursor cursor = DatabaseHelper.selectAll(WeatherInfoTable.NAME);

        final List<WeatherInfoModel> list = new ArrayList<>();

        while (cursor.moveToNext()){
            WeatherInfoModel model = new WeatherInfoModel();

            model.setDate(cursor.getString(WeatherInfoTable.ID_DATE));
            model.setInfo(new WeatherInfoModel.Info(new String[]{
                    cursor.getString(WeatherInfoTable.ID_DAY_ID),
                    cursor.getString(WeatherInfoTable.ID_DAY_WEA),
                    cursor.getString(WeatherInfoTable.ID_DAY_TMP),
                    cursor.getString(WeatherInfoTable.ID_DAY_WIND_DIR),
                    cursor.getString(WeatherInfoTable.ID_DAY_WIND_POWER),
                    cursor.getString(WeatherInfoTable.ID_DAY_TIME)
            },new String[]{
                    cursor.getString(WeatherInfoTable.ID_NIGHT_ID),
                    cursor.getString(WeatherInfoTable.ID_NIGHT_WEA),
                    cursor.getString(WeatherInfoTable.ID_NIGHT_TMP),
                    cursor.getString(WeatherInfoTable.ID_NIGHT_WIND_DIR),
                    cursor.getString(WeatherInfoTable.ID_NIGHT_WIND_POWER),
                    cursor.getString(WeatherInfoTable.ID_NIGHT_TIME)
            }));

            list.add(model);
        }

        //发送消息
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!list.isEmpty()) {
                    //从缓存获取成功　发送消息
                    EventBus.getDefault().post(new EventModel<WeatherInfoModel>(EVENT.WEATHER_INFO_LOAD_CACHE_SUCCESS,list));
                }else {
                    EventBus.getDefault().post(new EventModel<Void>(EVENT.WEATHER_INFO_LOAD_CACHE_FAILURE));
                }
            }
        });
    }

    @Override
    public void loadFromNet() {
        final Handler handler = new Handler(Looper.getMainLooper());

        NetManageUtil.get(WeatherApi.schoolWeatherUrl)
                .addTag(TAG)
                .enqueue(new JsonEntityCallback<WeatherInfoBean>() {
                    @Override
                    public void onFailure(IOException e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<Void>(EVENT.WEATHER_INFO_REFRESH_FAILURE));
                            }
                        });
                    }

                    @Override
                    public void onSuccess(final WeatherInfoBean entity, Headers headers) {
                        if (entity!=null&&entity.getError_code() == 0){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    List<WeatherInfoModel> list = Arrays.asList(entity.getResult().getData().getWeather());
                                    cacheAll(list);
                                    EventBus.getDefault().post(new EventModel<WeatherInfoModel>(EVENT.WEATHER_INFO_REFRESH_SUCCESS, list));
                                }
                            });
                        }else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<Void>(EVENT.WEATHER_INFO_REFRESH_FAILURE));
                                }
                            });
                        }
                    }
                });
    }
}
