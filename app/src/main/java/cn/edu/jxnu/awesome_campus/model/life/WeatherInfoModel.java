package cn.edu.jxnu.awesome_campus.model.life;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.life.WeatherInfoDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by MummyDing on 16-1-29.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class WeatherInfoModel implements IModel<WeatherInfoModel> {

    private WeatherInfoDAO dao;


    /**
     * 天气信息表
     * 当天气源返回的数据中有对应的名称时采用该名称命名字段，当没有对应名称时以数组名_对应下标命名
     */

    private String date;

    private Info info;

    public WeatherInfoModel() {
        dao = new WeatherInfoDAO();
    }

    @Override
    public boolean cacheAll(List<WeatherInfoModel> list) {
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



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static class Info{
        public Info(String[] day, String[] night) {
            this.night = night;
            this.day = day;
        }

        private String [] night;

        private String [] day;

        public String[] getNight() {
            return night;
        }

        public void setNight(String[] night) {
            this.night = night;
        }

        public String[] getDay() {
            return day;
        }

        public void setDay(String[] day) {
            this.day = day;
        }
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
