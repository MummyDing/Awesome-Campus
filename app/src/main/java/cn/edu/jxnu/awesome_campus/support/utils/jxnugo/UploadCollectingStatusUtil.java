package cn.edu.jxnu.awesome_campus.support.utils.jxnugo;

import android.util.Log;

import com.squareup.okhttp.Headers;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.model.jxnugo.CollectBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.CollectRTBean;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonCodeEntityCallback;

/**
 * Created by KevinWu on 16-5-15.
 */
public class UploadCollectingStatusUtil {
    public static final String TAG="UploadCollectingStatus.";

    public static void onUploadJson(boolean hasCollect,CollectBean pbean){
        SPUtil spu = new SPUtil(InitApp.AppContext);
        String userName = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
        String password = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
        if(hasCollect){
            NetManageUtil.postAuthJson(JxnuGoApi.BaseCollectUrl)
                    .addUserName(userName)
                    .addPassword(password)
                    .addTag(TAG)
                    .addJsonObject(pbean)
                    .enqueue(new JsonCodeEntityCallback<CollectRTBean>() {

                        @Override
                        public void onSuccess(CollectRTBean entity, int responseCode, Headers headers) {
                            if(responseCode==200){
                                Log.d(TAG,entity.getUncollectStatus());
                            }
                        }

                        @Override
                        public void onFailure(String error) {

                        }
                    });

        }else{

        }


    }
}
