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

    private WeatherInfoDAO weatherInfoDAO;
    /**
     * 天气信息表
     * 当天气源返回的数据中有对应的名称时采用该名称命名字段，当没有对应名称时以数组名_对应下标命名
     */
    private String city_name;//城市名
    private String temperature;//当前温度
    private String info;//天气信息
    private String day_2;//白天温度
    private String night_2;//夜间温度
    private String dataUptime;//更新时间
    private String direct;//风向
    private String power;//风力
    private String offset;//风向偏移量
    private String windspeed;//风速
    private String chuanyi_0;//穿衣建议短
    private String chuangyi_1;//穿衣建议长
    private String yundong_0;//运动建议短
    private String yundong_1;//运动建议长
    private String ganmao_0;//感冒指数短
    private String ganmao_1;//感冒指数长
    private String ziwaixian_0;//紫外线指数短
    private String ziwaixian_1;//紫外线指数长
    private String wuran_0;//污染指数短
    private String wuran_1;//污染指数长

    public WeatherInfoModel() {
        weatherInfoDAO = new WeatherInfoDAO();
    }

    public WeatherInfoModel(String city_name, String temperature, String info, String day_2, String night_2, String dataUptime, String direct, String power, String offset, String windspeed, String chuanyi_0, String chuangyi_1, String yundong_0, String yundong_1, String ganmao_0, String ganmao_1, String ziwaixian_0, String ziwaixian_1, String wuran_0, String wuran_1) {
        this();
        this.city_name = city_name;
        this.temperature = temperature;
        this.info = info;
        this.day_2 = day_2;
        this.night_2 = night_2;
        this.dataUptime = dataUptime;
        this.direct = direct;
        this.power = power;
        this.offset = offset;
        this.windspeed = windspeed;
        this.chuanyi_0 = chuanyi_0;
        this.chuangyi_1 = chuangyi_1;
        this.yundong_0 = yundong_0;
        this.yundong_1 = yundong_1;
        this.ganmao_0 = ganmao_0;
        this.ganmao_1 = ganmao_1;
        this.ziwaixian_0 = ziwaixian_0;
        this.ziwaixian_1 = ziwaixian_1;
        this.wuran_0 = wuran_0;
        this.wuran_1 = wuran_1;
    }


    @Override
    public void loadFromCache() {
        weatherInfoDAO.loadFromCache();
    }

    @Override
    public void loadFromNet() {
        weatherInfoDAO.loadFromNet();
    }

    @Override
    public boolean clearCache() {
        return weatherInfoDAO.clearCache();
    }

    @Override
    public boolean cacheAll(List<WeatherInfoModel> list) {
        return weatherInfoDAO.cacheAll(list);
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDay_2() {
        return day_2;
    }

    public void setDay_2(String day_2) {
        this.day_2 = day_2;
    }

    public String getNight_2() {
        return night_2;
    }

    public void setNight_2(String night_2) {
        this.night_2 = night_2;
    }

    public String getDataUptime() {
        return dataUptime;
    }

    public void setDataUptime(String dataUptime) {
        this.dataUptime = dataUptime;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(String windspeed) {
        this.windspeed = windspeed;
    }

    public String getChuanyi_0() {
        return chuanyi_0;
    }

    public void setChuanyi_0(String chuanyi_0) {
        this.chuanyi_0 = chuanyi_0;
    }

    public String getChuangyi_1() {
        return chuangyi_1;
    }

    public void setChuangyi_1(String chuangyi_1) {
        this.chuangyi_1 = chuangyi_1;
    }

    public String getYundong_0() {
        return yundong_0;
    }

    public void setYundong_0(String yundong_0) {
        this.yundong_0 = yundong_0;
    }

    public String getYundong_1() {
        return yundong_1;
    }

    public void setYundong_1(String yundong_1) {
        this.yundong_1 = yundong_1;
    }

    public String getGanmao_0() {
        return ganmao_0;
    }

    public void setGanmao_0(String ganmao_0) {
        this.ganmao_0 = ganmao_0;
    }

    public String getGanmao_1() {
        return ganmao_1;
    }

    public void setGanmao_1(String ganmao_1) {
        this.ganmao_1 = ganmao_1;
    }

    public String getZiwaixian_0() {
        return ziwaixian_0;
    }

    public void setZiwaixian_0(String ziwaixian_0) {
        this.ziwaixian_0 = ziwaixian_0;
    }

    public String getZiwaixian_1() {
        return ziwaixian_1;
    }

    public void setZiwaixian_1(String ziwaixian_1) {
        this.ziwaixian_1 = ziwaixian_1;
    }

    public String getWuran_0() {
        return wuran_0;
    }

    public void setWuran_0(String wuran_0) {
        this.wuran_0 = wuran_0;
    }

    public String getWuran_1() {
        return wuran_1;
    }

    public void setWuran_1(String wuran_1) {
        this.wuran_1 = wuran_1;
    }
}
