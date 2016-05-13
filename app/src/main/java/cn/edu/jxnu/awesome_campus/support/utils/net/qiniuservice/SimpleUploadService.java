package cn.edu.jxnu.awesome_campus.support.utils.net.qiniuservice;

/**
 * Created by zpauly on 16-5-13.
 */
public class SimpleUploadService extends IUploadService {
    private static SimpleUploadService instance;

    private SimpleUploadService() {
        setUploadManager();
    }

    public static SimpleUploadService getInstance() {
        if (instance == null) {
            synchronized (SimpleUploadService.class) {
                if (instance == null) {
                    instance = new SimpleUploadService();
                }
            }
        }
        return instance;
    }

    @Override
    public void setUploadManager() {
        this.manager = UploadConfig.getInstance()
                .getDefaultManager();
    }
}
