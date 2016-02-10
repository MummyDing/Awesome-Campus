package cn.edu.jxnu.awesome_campus.model.leisure;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.leisure.DailyDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by MummyDing on 16-2-10.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class DailyModel implements IModel<DailyModel> {

    private DailyDAO dailyDAO;
    private String date;
    private StoryBean[] stories;

    public DailyModel() {
        dailyDAO = new DailyDAO();
    }

    public DailyModel(String date, StoryBean[] stories) {
        this();
        this.date = date;
        this.stories = stories;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public StoryBean[] getStories() {
        return stories;
    }

    public void setStories(StoryBean[] stories) {
        this.stories = stories;
    }
}
