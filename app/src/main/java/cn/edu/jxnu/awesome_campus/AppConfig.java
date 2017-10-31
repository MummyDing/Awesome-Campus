package cn.edu.jxnu.awesome_campus;

import com.google.gson.Gson;
import com.squareup.okhttp.Headers;

import java.io.IOException;

import cn.edu.jxnu.awesome_campus.model.config.AppConfigModel;
import cn.edu.jxnu.awesome_campus.support.config.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;

/**
 * Created by wqd19 on 2017/10/31.
 */

public class AppConfig {
    public static void updateConfig(){
        NetManageUtil.get(Urlconfig.AppConfig_URL)
                .enqueue(new JsonEntityCallback<AppConfigModel>() {
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onSuccess(AppConfigModel entity, Headers headers) {
                        if(entity!=null){
                            new SPUtil(InitApp.AppContext).putStringSP("config","AppConfig",new Gson().toJson(entity));
                        }
                    }
                });
    }

    public static AppConfigModel getConfig(){
        AppConfigModel appConfigModel = new Gson().fromJson(new SPUtil(InitApp.AppContext).getStringSP("config","AppConfig"),AppConfigModel.class);
        return appConfigModel;
    }
}
