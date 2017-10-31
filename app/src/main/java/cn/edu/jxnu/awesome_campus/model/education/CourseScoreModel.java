package cn.edu.jxnu.awesome_campus.model.education;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.education.CourseScoreDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by MummyDing on 16-1-27.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseScoreModel implements IModel<CourseScoreModel> {


    private CourseScoreDAO courseScoreDAO;
    /***
     * 考试成绩表
     */
    private String Term;
    private String CourseID;
    private String CourseName;
    // 课程学分
    private String CourseCredit;
    // 课程成绩
    private String CourseScore;
    // 补考成绩
    private String AgainScore;
    // 标准分
    private String StandardScore;

    public CourseScoreModel() {
        courseScoreDAO = new CourseScoreDAO();
    }

    public CourseScoreModel(String term, String courseID, String courseName, String courseCredit, String courseScore, String againScore, String standardScore) {
        this();
        Term = term;
        CourseID = courseID;
        CourseName = courseName;
        CourseCredit = courseCredit;
        CourseScore = courseScore;
        AgainScore = againScore;
        StandardScore = standardScore;
    }


    @Override
    public void loadFromCache() {
        courseScoreDAO.loadFromCache();
    }

    @Override
    public void loadFromNet() {
        courseScoreDAO.loadFromNet();
    }

    @Override
    public boolean clearCache() {
        return courseScoreDAO.clearCache();
    }

    @Override
    public boolean cacheAll(List<CourseScoreModel> list) {
        return courseScoreDAO.cacheAll(list);
    }

    public String getTerm() {
        return Term;
    }

    public void setTerm(String term) {
        Term = term;
    }

    public String getCourseID() {
        return CourseID;
    }

    public void setCourseID(String courseID) {
        CourseID = courseID;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getCourseCredit() {
        return CourseCredit;
    }

    public void setCourseCredit(String courseCredit) {
        CourseCredit = courseCredit;
    }

    public String getCourseScore() {
        return CourseScore;
    }

    public void setCourseScore(String courseScore) {
        CourseScore = courseScore;
    }

    public String getAgainScore() {
        return AgainScore;
    }

    public void setAgainScore(String againScore) {
        AgainScore = againScore;
    }

    public String getStandardScore() {
        return StandardScore;
    }

    public void setStandardScore(String standardScore) {
        StandardScore = standardScore;
    }


}
