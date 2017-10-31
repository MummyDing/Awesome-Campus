package cn.edu.jxnu.awesome_campus.model.about;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.about.NotifyDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by MummyDing on 16-4-23.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class NotifyModel implements IModel<NotifyModel>{

    protected NotifyDAO notifyDAO;

    /**
     * 通知
     */
    private String notifyCode;

    private String title;


    //通知类型 link 或是 data
    private String type;

    private String data;


    private String date;//日期

    private int readed;//已读或未读，未读默认为false

    private NotifyModel(String notifyCode, String title, String type, String data, String date, int readed) {
        this.notifyCode = notifyCode;
        this.title = title;
        this.type = type;
        this.data = data;
        this.date = date;
        this.readed = readed;
    }

    public NotifyModel() {
        notifyDAO = new NotifyDAO();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getReaded() {
        return readed;
    }

    public void setReaded(int readed) {
        this.readed = readed;
    }

    @Override
    public boolean cacheAll(List<NotifyModel> list) {
        return notifyDAO.cacheAll(list);
    }

    @Override
    public boolean clearCache() {
        return notifyDAO.clearCache();
    }

    @Override
    public void loadFromCache() {
        notifyDAO.loadFromCache();
    }

    @Override
    public void loadFromNet() {
        notifyDAO.loadFromNet();
    }

    public String getNotifyCode() {
        return notifyCode;
    }

    public void setNotifyCode(String notifyCode) {
        this.notifyCode = notifyCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
