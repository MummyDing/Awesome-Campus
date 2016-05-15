package cn.edu.jxnu.awesome_campus.support.utils.jxnugo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.Toast;

import com.qiniu.android.http.ResponseInfo;
import com.squareup.okhttp.Headers;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsPhotoModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.PublishGoodsBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.QiniuUptokenBean;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;
import cn.edu.jxnu.awesome_campus.support.utils.net.qiniuservice.IUploadService;
import cn.edu.jxnu.awesome_campus.support.utils.net.qiniuservice.SimpleUploadService;
import cn.edu.jxnu.awesome_campus.support.utils.net.request.PostJsonRequest;

/**
 * Created by zpauly on 16-5-14.
 */
public class UploadGoodsUtil {
    public static ByteArrayOutputStream compressImages(String imagePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);
        return bos;
    }

    public static void onUploadImages(final Context context, List<GoodsPhotoModel> photoList) {
        IUploadService.OnUploadListener uploadListener = new IUploadService.OnUploadListener() {
            @Override
            public void onCompleted(String key, ResponseInfo info, JSONObject res) {

            }

            @Override
            public void onProcessing(String key, double percent) {

            }

            @Override
            public boolean onCancelled() {
                return false;
            }
        };


    }

    public static void onUploadJson(final PublishGoodsBean bean) {
        final PostJsonRequest request = new PostJsonRequest("");
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                request.addJsonObject(bean)
                        .enqueue(new JsonEntityCallback<GoodsModel>() {
                            @Override
                            public void onFailure(IOException e) {

                            }

                            @Override
                            public void onSuccess(GoodsModel entity, Headers headers) {

                            }
                        });
            }
        });
    }
}
