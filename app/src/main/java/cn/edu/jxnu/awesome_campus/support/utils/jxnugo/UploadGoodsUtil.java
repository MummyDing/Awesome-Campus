package cn.edu.jxnu.awesome_campus.support.utils.jxnugo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qiniu.android.http.ResponseInfo;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsPhotoModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.PhotokeyBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.PublishGoodsBean;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonCodeEntityCallback;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.NetCallback;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCodeCallback;
import cn.edu.jxnu.awesome_campus.support.utils.net.qiniuservice.IUploadService;
import cn.edu.jxnu.awesome_campus.support.utils.net.request.PostJsonRequest;
import cn.edu.jxnu.awesome_campus.support.utils.qiniu.UploadUtil;

/**
 * Created by zpauly on 16-5-14.
 */
public class UploadGoodsUtil {
    public static final String TAG = "UploadGoodsUtil";

    public static ByteArrayOutputStream compressImages(String imagePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);
        return bos;
    }

    public static void onUploadImages(final Context context, final List<GoodsPhotoModel> photoList) {
        final List<PhotokeyBean> keys = new ArrayList<>();
        IUploadService.OnUploadListener uploadListener = new IUploadService.OnUploadListener() {
            @Override
            public void onCompleted(String key, ResponseInfo info, JSONObject res) {
                if (!info.isOK()) {
                    EventBus.getDefault()
                            .post(new EventModel<String>(EVENT.GOODS_IMAGES_UPLOAD_FAIL
                                    , ""));
                    return;
                }
                PhotokeyBean bean;
                try {
                    bean = new PhotokeyBean(res.getString("key"));
                    keys.add(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (keys.size() == photoList.size()) {
                    EventBus.getDefault()
                            .post(new EventModel<List<PhotokeyBean>>(EVENT.GOODS_IMAGES_UPLOAD_SUCCESS
                                    , keys));
                }
            }

            @Override
            public void onProcessing(String key, double percent) {

            }

            @Override
            public boolean onCancelled() {
                return false;
            }
        };

        for (GoodsPhotoModel photoModel : photoList) {
            byte[] bytes = compressImages(photoModel.getPhotoPath()).toByteArray();
            UploadUtil.simpleUploadByBytes(bytes, uploadListener);
        }
    }

    public static void onUploadJson(final PublishGoodsBean bean, final Context context) {
        SPUtil spu = new SPUtil(InitApp.AppContext);
        final String userName = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
        final String password = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
        NetManageUtil.postAuthJson(JxnuGoApi.PublishNewPostUrl)
                .addUserName(userName)
                .addPassword(password)
                .addTag(TAG)
                .addJsonObject(bean)
                .enqueue(new StringCodeCallback() {
                    @Override
                    public void onSuccess(String result, int responseCode, Headers headers) {
                        Log.i(TAG, "" + responseCode);
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.i(TAG, error);
                    }
                });
    }
}
