package cn.edu.jxnu.awesome_campus.model.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.IModel;
import cn.edu.jxnu.awesome_campus.support.config.CourseTableRequestConfig;
import cn.edu.jxnu.awesome_campus.support.config.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;
import cn.finalteam.toolsfinal.io.FileUtils;

/**
 * Created by MummyDing on 2017/5/27.
 */

public class CourseTableRequestConfigModel implements IModel {

    private final static String COURSE_TABLE_REQUEST_CONFIG = "CourseTableRequestConfig.json";

    private String __VIEWSTATE;

    private String __EVENTVALIDATION;

    public String get__VIEWSTATE() {
        return __VIEWSTATE;
    }

    public String get__EVENTVALIDATION() {
        return __EVENTVALIDATION;
    }

    @Override
    public boolean cacheAll(List list) {
        if (isDataNull()) {
            return false;
        }
        File configFile = new File(InitApp.AppContext.getFilesDir() + COURSE_TABLE_REQUEST_CONFIG);
        try {
            if (configFile.exists() == false) {
                configFile.createNewFile();
            }
            Gson gson = new GsonBuilder().create();
            String jsonData = gson.toJson(this);
            FileUtils.writeStringToFile(configFile, jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache() {
        File configFile = new File(InitApp.AppContext.getFilesDir() + COURSE_TABLE_REQUEST_CONFIG);
        try {
            if (configFile.exists() == false) {
                configFile.createNewFile();
                if (isDataNull()) {
                    __VIEWSTATE = CourseTableRequestConfig.__VIEWSTATE;
                    __EVENTVALIDATION = CourseTableRequestConfig.__EVENTVALIDATION;
                }
                cacheAll(null);
                return;
            }
            String jsonData = FileUtils.readFileToString(configFile);
            Gson gson = new GsonBuilder().create();
            CourseTableRequestConfigModel model = gson.fromJson(jsonData, CourseTableRequestConfigModel.class);
            fillParams(model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadFromNet() {
        NetManageUtil.get(Urlconfig.CourseTablePostParams_URL)
                .enqueue(new JsonEntityCallback<CourseTableRequestConfigModel>() {
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onSuccess(CourseTableRequestConfigModel entity, Headers headers) {
                        fillParams(entity);
                        cacheAll(null);
                        // 重新请求课表
                        EventBus.getDefault().post(new EventModel<Void>(EVENT.COURSE_TABLE_REQUEST_REFRESH));
                    }
                });
    }

    private void fillParams(CourseTableRequestConfigModel model) {
        if (model != null && !model.isDataNull()) {
            __VIEWSTATE = model.get__VIEWSTATE();
            __EVENTVALIDATION = model.get__EVENTVALIDATION();
        }
    }

    private boolean isDataNull() {
        return TextUtil.isNull(__VIEWSTATE) || TextUtil.isNull(__EVENTVALIDATION);
    }
}
