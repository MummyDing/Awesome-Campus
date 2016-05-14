package cn.edu.jxnu.awesome_campus.support.utils.net.qiniuservice;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * Created by zpauly on 16-5-13.
 */
public abstract class IUploadService {
    protected OnUploadListener uploadListener;

    protected UploadManager manager;

    private UpCompletionHandler handler;

    private UploadOptions options;

    public interface OnUploadListener {
        void onCompleted(String key, ResponseInfo info, JSONObject res);

        void onProcessing(String key, double percent);

        boolean onCancelled();
    }

    public void setOnUploadListener(OnUploadListener listener) {
        uploadListener = listener;
    }

    public abstract void setUploadManager() throws IOException;

    public void putDataByBytes(byte[] bytes, String key, String token) {
        if (manager == null) {
            return;
        }
        setCallbacks();
        manager.put(bytes, key, token, handler, options);
    }

    public void putDataByPath(String path, String key, String token) {
        if (manager == null) {
            return;
        }
        setCallbacks();
        manager.put(path, key, token, handler, options);
    }

    public void putDataByFile(File file, String key, String token) {
        if (manager == null) {
            return;
        }
        setCallbacks();
        manager.put(file, key, token, handler, options);
    }

    private void setCallbacks() {
        handler = new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (uploadListener == null) {
                    return;
                }
                uploadListener.onCompleted(key, info, response);
            }
        };

        options = new UploadOptions(null, null, false,
                new UpProgressHandler() {
                    @Override
                    public void progress(String key, double percent) {
                        if (uploadListener == null) {
                            return;
                        }
                        uploadListener.onProcessing(key, percent);
                    }
                }, new UpCancellationSignal() {
            @Override
            public boolean isCancelled() {
                if (uploadListener == null) {
                    return false;
                }
                return uploadListener.onCancelled();
            }
        });
    }
}
