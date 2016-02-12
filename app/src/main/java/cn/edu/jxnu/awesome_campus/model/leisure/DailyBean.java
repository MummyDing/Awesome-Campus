package cn.edu.jxnu.awesome_campus.model.leisure;

/**
 * Created by MummyDing on 16-2-12.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class DailyBean {
    private String date;
    private DailyModel[] stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DailyModel[] getStories() {
        return stories;
    }

    public void setStories(DailyModel[] stories) {
        this.stories = stories;
    }
}
