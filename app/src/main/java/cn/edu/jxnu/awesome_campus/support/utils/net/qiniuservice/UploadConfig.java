package cn.edu.jxnu.awesome_campus.support.utils.net.qiniuservice;

import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.Recorder;
import com.qiniu.android.storage.UploadManager;

/**
 * Created by zpauly on 16-5-13.
 */
public class UploadConfig {
    private static UploadConfig instance;

    private UploadManager defaultManager;

    private UploadManager customManager;

    private Configuration.Builder configBuilder;

    private UploadConfig() {
        configBuilder = new Configuration.Builder();
    }

    public static UploadConfig getInstance() {
        if (instance == null) {
            synchronized (UploadConfig.class) {
                if (instance == null) {
                    instance = new UploadConfig();
                }
            }
        }
        return instance;
    }

    /**
     * 分片上传时，每片的大小。 默认 256K
     * @param chunkSize
     * @return
     */
    public UploadConfig setChunkSize(int chunkSize) {
        configBuilder.chunkSize(chunkSize);
        return this;
    }

    /**
     * 启用分片上传阀值。默认 512K
     * @param threshhold
     * @return
     */
    public UploadConfig putThreshhold(int threshhold) {
        configBuilder.putThreshhold(threshhold);
        return this;
    }

    /**
     * 链接超时。默认 10秒
     * @param timeout
     * @return
     */
    public UploadConfig setConnectTimeout(int timeout) {
        configBuilder.connectTimeout(timeout);
        return this;
    }

    /**
     * 服务器响应超时。默认 60秒
     * @param timeout
     * @return
     */
    public UploadConfig setResponseTimeout(int timeout) {
        configBuilder.responseTimeout(timeout);
        return this;
    }

    /**
     * recorder 分片上传时，已上传片记录器。默认 null
     * @param recorder
     * @return
     */
    public UploadConfig setRecorder(Recorder recorder) {
        configBuilder.recorder(recorder);
        return this;
    }

    public UploadManager getDefaultManager() {
        if (defaultManager == null) {
            synchronized (UploadConfig.class) {
                if (defaultManager == null) {
                    Configuration config = new Configuration.Builder().build();
                    defaultManager = new UploadManager(config);
                }
            }
        }
        return defaultManager;
    }

    public UploadManager getCustomManager() {
        Configuration config = configBuilder.build();
        customManager = new UploadManager(config);
        return customManager;
    }
}
