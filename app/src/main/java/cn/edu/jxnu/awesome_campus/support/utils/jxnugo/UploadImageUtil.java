package cn.edu.jxnu.awesome_campus.support.utils.jxnugo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.qiniu.android.http.ResponseInfo;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsPhotoModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.PhotokeyBean;
import cn.edu.jxnu.awesome_campus.support.utils.net.qiniuservice.IUploadService;
import cn.edu.jxnu.awesome_campus.support.utils.qiniu.UploadUtil;

/**
 * Created by zpauly on 16-5-19.
 */
public class UploadImageUtil {
    public static final String TAG="UploadImageUtil";
    private static Handler handler=new Handler(Looper.getMainLooper());
    public static String getMimeTypeFromUrl(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }
    public static ByteArrayOutputStream compressImages(String imagePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        if (bitmap == null) {
            return null;
        }
        float width=bitmap.getWidth();
        float height=bitmap.getHeight();
        while(width>800||height>800){
            Log.d(TAG,"上传的图片大小 宽："+width+" 高："+height);
            float scaleWidth=0;
            float scaleHeight=0;
            if(width>height){
                scaleWidth= (width/2) /width;
                scaleHeight= (height/2) /height;
            }else{
                scaleWidth= (width/2) /height;
                scaleHeight= (height/2) /width;
            }
            Matrix matrix=new Matrix();
            matrix.postScale(scaleWidth,scaleHeight);
            bitmap=Bitmap.createBitmap(bitmap,0,0,(int)width,(int)height,matrix,true);
            width=bitmap.getWidth();
            height=bitmap.getHeight();
        }
        Log.d(TAG,"最终图片大小 宽："+width+" 高："+height);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bos);
        return bos;
    }
    public static void onUploadImages(final Context context, final List<String> photoPath) {
        final List<PhotokeyBean> keys = new ArrayList<>();
        IUploadService.OnUploadListener uploadListener = new IUploadService.OnUploadListener() {
            @Override
            public void onCompleted(String key, ResponseInfo info, JSONObject res) {
                if (!info.isOK()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault()
                                    .post(new EventModel<String>(EVENT.IMAGES_UPLOAD_FAILURE
                                            , ""));
                        }
                    });

                    return;
                }
                PhotokeyBean bean;
                try {
                    bean = new PhotokeyBean(res.getString("key"));
                    keys.add(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (keys.size() == photoPath.size()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault()
                                    .post(new EventModel<List<PhotokeyBean>>(EVENT.IMAGES_UPLOAD_SUCCESS
                                            , keys));
                        }
                    });

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
        for (String path: photoPath) {
            byte[] bytes = compressImages(path).toByteArray();
            UploadUtil.simpleUploadByBytes(bytes, uploadListener);
        }
    }
}
