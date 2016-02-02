package cn.edu.jxnu.awesome_campus.model.home;

import java.util.List;

import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by MummyDing on 16-1-27.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseTableModel implements IModel<CourseTableModel>{
    /***
     * 课程表
     */
    // 礼拜几 DayOfWeek
    private int DayOfWeek;
    // 第几学期
    private  String Term;
    // 每节课的课程信息
    // 课程名+地点 中间用@分割  然后再根据课程名可以到课程信息表中查询具体课程信息
    private  String OneTwo;
    private  String Three;
    private  String Four;
    private  String Five;
    private  String SixSeven;
    private  String EightNine;
    // 晚上的课
    private  String Night;


    public CourseTableModel() {
    }

    public CourseTableModel(int dayOfWeek, String term, String oneTwo, String three, String four, String five, String sixSeven, String eightNine, String night) {
        DayOfWeek = dayOfWeek;
        Term = term;
        OneTwo = oneTwo;
        Three = three;
        Four = four;
        Five = five;
        SixSeven = sixSeven;
        EightNine = eightNine;
        Night = night;
    }


    /**
     * 从网络拉取数据
     * @return
     */
    @Override
    public void loadFromNet() {
    }

    /***
     * 从数据库从获取数据
     * @return
     */

    @Override
    public void loadFromCache() {
    }

    /**
     * 清除缓存
     * @return
     */
    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public boolean cacheAll() {
        return false;
    }

    public int getDayOfWeek() {
        return DayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        DayOfWeek = dayOfWeek;
    }

    public String getTerm() {
        return Term;
    }

    public void setTerm(String term) {
        Term = term;
    }

    public String getOneTwo() {
        return OneTwo;
    }

    public void setOneTwo(String oneTwo) {
        OneTwo = oneTwo;
    }

    public String getThree() {
        return Three;
    }

    public void setThree(String three) {
        Three = three;
    }

    public String getFour() {
        return Four;
    }

    public void setFour(String four) {
        Four = four;
    }

    public String getFive() {
        return Five;
    }

    public void setFive(String five) {
        Five = five;
    }

    public String getSixSeven() {
        return SixSeven;
    }

    public void setSixSeven(String sixSeven) {
        SixSeven = sixSeven;
    }

    public String getEightNine() {
        return EightNine;
    }

    public void setEightNine(String eightNine) {
        EightNine = eightNine;
    }

    public String getNight() {
        return Night;
    }

    public void setNight(String night) {
        Night = night;
    }

}
