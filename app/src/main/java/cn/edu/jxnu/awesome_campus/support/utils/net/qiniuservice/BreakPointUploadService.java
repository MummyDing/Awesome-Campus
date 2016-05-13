package cn.edu.jxnu.awesome_campus.support.utils.net.qiniuservice;

import com.qiniu.android.storage.Recorder;
import com.qiniu.android.storage.persistent.FileRecorder;

import java.io.IOException;

/**
 * Created by zpauly on 16-5-13.
 */
public class BreakPointUploadService extends IUploadService {
    private static BreakPointUploadService instance;

    private BreakPointUploadService() {
        setUploadManager();
    }

    public static BreakPointUploadService getInstance() {
        if (instance == null) {
            synchronized (BreakPointUploadService.class) {
                if (instance == null) {
                    instance = new BreakPointUploadService();
                }
            }
        }
        return instance;
    }

    @Override
    public void setUploadManager() {
        Recorder recorder = null;
        try {
            recorder = new FileRecorder("/data/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.manager = UploadConfig.getInstance()
                .setRecorder(recorder)
                .getCustomManager();
    }
}
