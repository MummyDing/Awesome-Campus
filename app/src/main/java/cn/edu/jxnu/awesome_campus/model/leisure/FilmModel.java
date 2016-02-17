package cn.edu.jxnu.awesome_campus.model.leisure;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.leisure.FilmDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * 简书拉取的数据的model
 * Created by KevinWu on 2016/2/16.
 */
public class FilmModel implements IModel<FilmModel> {

    private FilmDAO dao;
    private String url;//内容url
    private String title;//标题
    private String readingCount;//阅读量
    private String detail;//内容
    private String topPic;//标题图片url


    public FilmModel(String url, String title, String readingCount, String detail, String topPic) {
        this();
        this.url = url;
        this.title = title;
        this.readingCount = readingCount;
        this.detail = detail;
        this.topPic = topPic;
    }

    public FilmModel() {
        dao = new FilmDAO();
    }

    @Override
    public boolean cacheAll(List<FilmModel> list) {
        return dao.cacheAll(list);
    }

    @Override
    public boolean clearCache() {
        return dao.clearCache();
    }

    @Override
    public void loadFromCache() {
        dao.loadFromCache();
    }

    @Override
    public void loadFromNet() {
        dao.loadFromNet();
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

    public String getReadingCount() {
        return readingCount;
    }

    public void setReadingCount(String readingCount) {
        this.readingCount = readingCount;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTopPic() {
        return topPic;
    }

    public void setTopPic(String topPic) {
        this.topPic = topPic;
    }
}
