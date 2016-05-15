package cn.edu.jxnu.awesome_campus.support.utils.jxnugo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

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

import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsPhotoModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.PublishGoodsBean;
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
        final List<String> keys = new ArrayList<>();
        IUploadService.OnUploadListener uploadListener = new IUploadService.OnUploadListener() {
            @Override
            public void onCompleted(String key, ResponseInfo info, JSONObject res) {
                try {
                    keys.add(res.getString("key"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (keys.size() == photoList.size()) {
                    Toast.makeText(context, String.valueOf(keys.size()), Toast.LENGTH_SHORT).show();
                    final StringBuffer stringBuffer = new StringBuffer();
                    for (String str : keys)
                        stringBuffer.append(str + "/");
                    EventBus.getDefault()
                            .post(new EventModel<String>(EVENT.GOODS_IMAGES_UPLOAD_SUCCESS
                                    , stringBuffer.toString()));
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

    public static void onUploadJson(final PublishGoodsBean bean) {
        final PostJsonRequest request = new PostJsonRequest(JxnuGoApi.PublishNewPostUrl);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                request.addJsonObject(bean)
                        .enqueue(new StringCodeCallback() {
                            @Override
                            public void onSuccess(String result, int responseCode, Headers headers) {
                                Log.i(TAG, "" + responseCode);
                                if (responseCode == 200) {
                                    EventBus.getDefault().post(new EventModel<Void>(EVENT.POST_UPLOAD_SUCCESS));
                                }
                            }

                            @Override
                            public void onFailure(String error) {

                            }
                        });
            }
        });
    }
}
