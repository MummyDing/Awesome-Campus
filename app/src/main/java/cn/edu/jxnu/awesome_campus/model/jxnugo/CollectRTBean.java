package cn.edu.jxnu.awesome_campus.model.jxnugo;

/**
 * Created by KevinWu on 16-5-15.
 */
public class CollectRTBean {
    public String getUncollectStatus() {
        return uncollectStatus;
    }

    public void setUncollectStatus(String uncollectStatus) {
        this.uncollectStatus = uncollectStatus;
    }

    private String uncollectStatus;

    public String getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(String collectStatus) {
        this.collectStatus = collectStatus;
    }

    private String collectStatus;
}
