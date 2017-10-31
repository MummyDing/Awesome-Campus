package cn.edu.jxnu.awesome_campus.database.dao.home;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.database.DatabaseHelper;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.database.table.home.CampusNewsTable;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.home.CampusNewsModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.home.CampusNewsParse;
import cn.edu.jxnu.awesome_campus.support.config.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;

/**
 * Created by MummyDing on 16-2-2.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CampusNewsDAO implements DAO<CampusNewsModel> {
    public static final String TAG="CampusNewsDAO";
    @Override
    public boolean cacheAll(List<CampusNewsModel> list) {

        if (list == null || list.size() == 0){
            return false;
        }
        clearCache();

        for (int i=0 ; i<list.size() ; i++){
            CampusNewsModel model = list.get(i);
            ContentValues values = new ContentValues();
            values.put(CampusNewsTable.NEWS_TITLE,model.getNewsTitle());
            values.put(CampusNewsTable.NEWS_TIME,model.getNewsTime());
            values.put(CampusNewsTable.NEWS_URL,model.getNewsURL());
            values.put(CampusNewsTable.NEWS_PIC_URL,model.getNewsPicURL());
            values.put(CampusNewsTable.UPDATE_TIME,model.getUpdateTime());
            DatabaseHelper.insert(CampusNewsTable.NAME,values);
        }
        return true;
    }

    @Override
    public boolean clearCache() {
        DatabaseHelper.clearTable(CampusNewsTable.NAME);
        return true;
    }

    @Override
    public void loadFromCache() {
        Cursor cursor = DatabaseHelper.selectAll(CampusNewsTable.NAME);
        final List<CampusNewsModel> list = new ArrayList<>();

        while (cursor.moveToNext()){
            CampusNewsModel model = new CampusNewsModel();
            model.setNewsTitle(cursor.getString(CampusNewsTable.ID_NEWS_TITLE));
            model.setNewsTime(cursor.getString(CampusNewsTable.ID_NEWS_TIME));
            model.setNewsURL(cursor.getString(CampusNewsTable.ID_NEWS_URL));
            model.setNewsPicURL(cursor.getString(CampusNewsTable.ID_NEWS_PIC_URL));
            model.setUpdateTime(cursor.getString(CampusNewsTable.ID_UPDATE_TIME));
            model.setNewsDetails(cursor.getString(CampusNewsTable.ID_NEWS_DETAILS));
            list.add(model);
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (list!=null &&list.size() != 0){
                    // 从缓存获取成功 发送消息
                    EventBus.getDefault().post(new EventModel<CampusNewsModel>(EVENT.CAMPUS_NEWS_LOAD_CACHE_SUCCESS,list));
                }else{
                    //从缓存获取失败
                    EventBus.getDefault().post(new EventModel<CampusNewsModel>(EVENT.CAMPUS_NEWS_LOAD_CACHE_FAILURE));
                }
            }
        });
    }

    private int count = 1;
    private Handler handler = new Handler(Looper.getMainLooper());
    @Override
    public void loadFromNet() {

        final List<CampusNewsModel> resultList = new ArrayList<CampusNewsModel>();
        
        NetManageUtil.get(Urlconfig.CampusNews_YW_URL)
                .addTag(TAG)
                .enqueue(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Headers headers) {
                        CampusNewsParse myParse=new CampusNewsParse(result);
                        resultList.addAll(myParse.getEndList());
                        if(count == 3 && resultList.size()>0){
                            sendSuccess(resultList);
                        }
                        count++;
                    }
                    @Override
                    public void onFailure(String error) {
                        if(count == 3 && resultList.size() == 0){
                            sendFailure();
                        }else if(count == 3){
                            sendSuccess(resultList);
                        }
                        count++;
                    }
                });

        NetManageUtil.get(Urlconfig.CampusNews_DT_URL)
                .addTag(TAG)
                .enqueue(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Headers headers) {
                        CampusNewsParse myParse=new CampusNewsParse(result);
                        resultList.addAll(myParse.getEndList());
                        if(count == 3 && resultList.size()>0){
                            sendSuccess(resultList);
                        }
                        count++;
                    }
                    @Override
                    public void onFailure(String error) {
                        if(count == 3 && resultList.size() == 0){
                            sendFailure();
                        }else if(count == 3){
                            sendSuccess(resultList);
                        }
                        count++;
                    }
                });

        NetManageUtil.get(Urlconfig.CampusNews_MT_URL)
                .addTag(TAG)
                .enqueue(new StringCallback() {
                    @Override
                    public  void onSuccess(String result, Headers headers) {
                        CampusNewsParse myParse=new CampusNewsParse(result);
                        resultList.addAll(myParse.getEndList());
                        if(count == 3 && resultList.size()>0){
                            sendSuccess(resultList);
                        }
                        count++;
                    }

                    @Override
                    public  void onFailure(String error) {
                        if(count == 3 && resultList.size() == 0){
                            sendFailure();
                        }else if(count == 3){
                            sendSuccess(resultList);
                        }
                        count++;
                    }
                });
    }

    private void sendSuccess(final List<CampusNewsModel> result){
        handler.post(new Runnable() {
            @Override
            public void run() {
                count = 1;
                cacheAll(result);
                EventBus.getDefault().post(new EventModel<CampusNewsModel>(EVENT.CAMPUS_NEWS_REFRESH_SUCCESS, result));
            }
        });
    }

    private void sendFailure(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                count = 1;
                EventBus.getDefault().post(new EventModel<CampusNewsModel>(EVENT.CAMPUS_NEWS_REFRESH_FAILURE));
            }
        });
    }

}
