package cn.edu.jxnu.awesome_campus.model.home;

import android.util.Log;

import java.util.Collection;
import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.home.CampusNewsDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by MummyDing on 16-1-27.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CampusNewsModel  implements IModel<CampusNewsModel>,Comparable<CampusNewsModel>{


    private CampusNewsDAO campusNewsDAO;
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

    // 新闻详情
    private String NewsDetails;

    public CampusNewsModel() {
        campusNewsDAO = new CampusNewsDAO();
    }


    public CampusNewsModel(String newsTitle, String newsTime, String newsURL, String newsPicURL, String updateTime, String newsDetails) {
        NewsTitle = newsTitle;
        NewsTime = newsTime;
        NewsURL = newsURL;
        NewsPicURL = newsPicURL;
        UpdateTime = updateTime;
        NewsDetails = newsDetails;
    }

    @Override
    public boolean clearCache() {
        return campusNewsDAO.clearCache();
    }

    @Override
    public void loadFromCache() {
        campusNewsDAO.loadFromCache();
    }

    @Override
    public void loadFromNet() {
        campusNewsDAO.loadFromNet();
    }

    @Override
    public boolean cacheAll(List<CampusNewsModel> list) {
        return campusNewsDAO.cacheAll(list);
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

    @Override
    public int compareTo(CampusNewsModel model) {
        String str1 = model.getNewsTime();
        String str2 = this.getNewsTime();
        if(str1.equals(str2)){
            return 0;
        }
        int len = str1.length();
        for(int i=0 ; i<len; i++){
            if(str1.charAt(i)>str2.charAt(i)){
                return 1;
            }else if(str1.charAt(i)<str2.charAt(i)){
                return -1;
            }
        }
        return 0;
    }

    public String getNewsDetails() {
        return NewsDetails;
    }

    public void setNewsDetails(String newsDetails) {
        NewsDetails = newsDetails;
    }
}
