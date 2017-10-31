package cn.edu.jxnu.awesome_campus.support.utils.qiniu;

import com.squareup.okhttp.Headers;

import java.io.File;
import java.io.IOException;

import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.model.jxnugo.QiniuUptokenBean;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;
import cn.edu.jxnu.awesome_campus.support.utils.net.qiniuservice.IUploadService;
import cn.edu.jxnu.awesome_campus.support.utils.net.qiniuservice.SimpleUploadService;

/**
 * Created by zpauly on 16-5-14.
 */
public class UploadUtil {
    private static SimpleUploadService service;

    static {
        service = SimpleUploadService.getInstance();
    }

    public static void simpleUploadByPath(final String path, IUploadService.OnUploadListener uploadListener) {
        service.setOnUploadListener(uploadListener);
        NetManageUtil.get(JxnuGoApi.QiniuToken)
                .enqueue(new JsonEntityCallback<QiniuUptokenBean>() {
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onSuccess(QiniuUptokenBean entity, Headers headers) {
                        service.putDataByPath(path, null, entity.getUptoken());
                    }
                });
    }

    public static void simpleUploadByBytes(final byte[] bytes, IUploadService.OnUploadListener uploadListener) {
        service.setOnUploadListener(uploadListener);
        NetManageUtil.get(JxnuGoApi.QiniuToken)
                .enqueue(new JsonEntityCallback<QiniuUptokenBean>() {
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onSuccess(QiniuUptokenBean entity, Headers headers) {
                        service.putDataByBytes(bytes, null, entity.getUptoken());
                    }
                });
    }

    public static void simpleUploadByFile(final File file, IUploadService.OnUploadListener uploadListener) {
        service.setOnUploadListener(uploadListener);
        NetManageUtil.get(JxnuGoApi.QiniuToken)
                .enqueue(new JsonEntityCallback<QiniuUptokenBean>() {
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onSuccess(QiniuUptokenBean entity, Headers headers) {
                        service.putDataByFile(file, null, entity.getUptoken());
                    }
                });
    }
}
