package cn.edu.jxnu.awesome_campus.model.leisure;

import java.util.List;

import cn.edu.jxnu.awesome_campus.api.DailyApi;
import cn.edu.jxnu.awesome_campus.database.dao.leisure.DailyDetailsDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by MummyDing on 16-2-10.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class DailyDetailsModel implements IModel<DailyDetailsModel> {



    private DailyDetailsDAO dailyDetailsDAO;

    private String body;
    private String image;
    private String title;

    // 文章url 需要根据id拼接
    private String url;


    public DailyDetailsModel(DailyDetailsDAO dailyDetailsDAO, String body, String image, String title, int id) {
        this();
        this.dailyDetailsDAO = dailyDetailsDAO;
        this.body = body;
        this.image = image;
        this.title = title;
        setUrl(id);
    }

    public DailyDetailsModel() {
        dailyDetailsDAO = new DailyDetailsDAO();
    }

    @Override
    public boolean cacheAll(List<DailyDetailsModel> list) {
        return dailyDetailsDAO.cacheAll(list);
    }

    @Override
    public boolean clearCache() {
        return dailyDetailsDAO.clearCache();
    }

    @Override
    public void loadFromCache() {
        dailyDetailsDAO.loadFromCache();
    }

    @Override
    public void loadFromNet() {

        if(url == null) return;
        dailyDetailsDAO.setUrl(url);
        dailyDetailsDAO.loadFromNet();
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

    public String getUrl() {
        return url;
    }

    public void setUrl(int id) {
        this.url = DailyApi.daily_details_url+id;
    }
}
