package cn.edu.jxnu.awesome_campus.model.leisure;

/**
 * Created by MummyDing on 16-2-10.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class DailyDetailsBean {

    private String body;
    private String image;
    private String title;
    private String share_url;

    // 文章url 需要根据id拼接
    private String url;

    public DailyDetailsBean() {
    }

    public DailyDetailsBean(String body, String image, String title, String share_url, String url) {
        this.body = body;
        this.image = image;
        this.title = title;
        this.share_url = share_url;
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
