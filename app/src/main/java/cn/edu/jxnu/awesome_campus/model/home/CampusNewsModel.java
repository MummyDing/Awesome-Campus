package cn.edu.jxnu.awesome_campus.model.home;

import java.util.List;

import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by MummyDing on 16-1-27.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CampusNewsModel implements IModel<CourseTableModel> {


    /**
     * 校内新闻
     */
    private String NewsTitle;
    // 新闻发布时间
    private String NewsTime;
    private String NewsURL;
    // 标题图链接
    private String NewsPicURL;
    // 新闻拉取到本地的时间
    private String UpdateTime;

    public CampusNewsModel() {
    }

    @Override
    public void loadFromNet() {

    }

    public CampusNewsModel(String newsTitle, String newsTime, String newsURL, String newsPicURL, String updateTime) {
        NewsTitle = newsTitle;
        NewsTime = newsTime;
        NewsURL = newsURL;
        NewsPicURL = newsPicURL;
        UpdateTime = updateTime;
    }

    @Override
    public void loadFromCache() {

    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public boolean cacheAll() {
        return false;
    }

    public String getNewsTitle() {
        return NewsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        NewsTitle = newsTitle;
    }

    public String getNewsTime() {
        return NewsTime;
    }

    public void setNewsTime(String newsTime) {
        NewsTime = newsTime;
    }

    public String getNewsURL() {
        return NewsURL;
    }

    public void setNewsURL(String newsURL) {
        NewsURL = newsURL;
    }

    public String getNewsPicURL() {
        return NewsPicURL;
    }

    public void setNewsPicURL(String newsPicURL) {
        NewsPicURL = newsPicURL;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }
}
