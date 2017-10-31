package cn.edu.jxnu.awesome_campus.model.leisure;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.leisure.ScienceDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by MummyDing on 16-2-12.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ScienceModel implements IModel<ScienceModel>{

    private ScienceDAO scienceDAO;

    private int replies_count;
    private Image_info image_info = new Image_info();
    private String url;
    private String title;
    private String scienceDetails;

    public void setScienceDetails(String scienceDetails) {
        this.scienceDetails = scienceDetails;
    }

    public String getScienceDetails() {
        return scienceDetails;
    }



    public ScienceModel() {
        scienceDAO = new ScienceDAO();
    }

    public ScienceModel(int replies_count, Image_info image_info, String url, String title, String scienceDetails) {
        this();
        this.replies_count = replies_count;
        this.image_info = image_info;
        this.url = url;
        this.title = title;
        this.scienceDetails = scienceDetails;
    }

    @Override
    public boolean cacheAll(List<ScienceModel> list) {
        return scienceDAO.cacheAll(list);
    }

    @Override
    public boolean clearCache() {
        return scienceDAO.clearCache();
    }

    @Override
    public void loadFromCache() {
        scienceDAO.loadFromCache();
    }

    @Override
    public void loadFromNet() {
        scienceDAO.loadFromNet();
    }

    public class Image_info {
        String url;
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
    }


    public int getReplies_count() {
        return replies_count;
    }

    public void setReplies_count(int replies_count) {
        this.replies_count = replies_count;
    }

    public Image_info getImage_info() {
        return image_info;
    }

    public void setImage_info(Image_info image_info) {
        this.image_info = image_info;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
