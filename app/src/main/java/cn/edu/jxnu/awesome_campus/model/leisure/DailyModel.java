package cn.edu.jxnu.awesome_campus.model.leisure;

import com.squareup.okhttp.Headers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.api.DailyApi;
import cn.edu.jxnu.awesome_campus.database.dao.leisure.DailyDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;

/**
 * Created by MummyDing on 16-2-10.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class DailyModel implements IModel<DailyModel> {


    private DailyDAO dailyDAO;
    private String title;
    private int id;

    private String body;
    //private String largepic;
    private String [] images;


    public DailyModel() {
        dailyDAO = new DailyDAO();
    }

    public DailyModel(String title, int id, String body, String[] images) {
        this.title = title;
        this.id = id;
        this.body = body;
        this.images = images;
    }

    @Override
    public boolean cacheAll(List<DailyModel> list) {
        return dailyDAO.cacheAll(list);
    }

    @Override
    public boolean clearCache() {
        return dailyDAO.clearCache();
    }

    @Override
    public void loadFromCache() {
        dailyDAO.loadFromCache();
    }

    @Override
    public void loadFromNet() {
        dailyDAO.loadFromNet();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    /*public String getLargepic() {
        return largepic;
    }

    public void setLargepic(String largepic) {
        this.largepic = largepic;
    }*/

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}
