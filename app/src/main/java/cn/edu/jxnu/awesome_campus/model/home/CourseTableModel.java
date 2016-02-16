package cn.edu.jxnu.awesome_campus.model.home;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.home.CourseTableDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;

/**
 * Created by MummyDing on 16-1-27.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseTableModel implements IModel<CourseTableModel>{

    private CourseTableDAO courseTableDAO;
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


    private List<CourseBean> courseList;

    public CourseTableModel() {
        courseTableDAO = new CourseTableDAO();
    }

    public CourseTableModel(int dayOfWeek, String term, String oneTwo, String three, String four, String five, String sixSeven, String eightNine, String night) {
        this();
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


    @Override
    public void loadFromCache() {
        courseTableDAO.loadFromCache();
    }

    @Override
    public void loadFromNet() {
        courseTableDAO.loadFromNet();
    }

    @Override
    public boolean clearCache() {
        return courseTableDAO.clearCache();
    }

    @Override
    public boolean cacheAll(List<CourseTableModel> list) {
        return courseTableDAO.cacheAll(list);
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


    public List<CourseBean> getCourseList() {
        if(courseList != null){
            return courseList;
        }
        courseList = new ArrayList<>();
        if(TextUtil.isNull(getOneTwo()) == false){
            String [] str = getOneTwo().split("@");
            courseList.add(new CourseBean(0,str[0].trim(),str[1].trim()));
        }
        if(TextUtil.isNull(getThree()) == false){
            String [] str = getThree().split("@");
            courseList.add(new CourseBean(1,str[0].trim(),str[1].trim()));
        }
        if(TextUtil.isNull(getFour()) == false){
            String [] str = getFour().split("@");
            courseList.add(new CourseBean(2,str[0].trim(),str[1].trim()));
        }
        if(TextUtil.isNull(getFive()) == false){
            String [] str = getFive().split("@");
            courseList.add(new CourseBean(3,str[0].trim(),str[1].trim()));
        }
        if(TextUtil.isNull(getSixSeven()) == false){
            String [] str = getSixSeven().split("@");
            courseList.add(new CourseBean(4,str[0].trim(),str[1].trim()));
        }
        if(TextUtil.isNull(getEightNine()) == false){
            String [] str = getEightNine().split("@");
            courseList.add(new CourseBean(5,str[0].trim(),str[1].trim()));
        }

        if(TextUtil.isNull(getNight()) == false){
            String [] str = getNight().split("@");
            courseList.add(new CourseBean(6,str[0].trim(),str[1].trim()));
        }
        return courseList;
    }
}
