package cn.edu.jxnu.awesome_campus.model.home;

import java.util.List;

import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by MummyDing on 16-1-27.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseInfoModel implements IModel<CourseInfoModel> {


    /**
     * 课程信息
     */
    private String CourseName;
    private String CourseID;
    private String CourseTeacher;
    //  课程班级
    private String CourseClass;
    // 该课程全部同学名单链接
    private String ClassmateListLink;
    // 该课程课程讨论区链接
    private String ClassForumLink;

    public CourseInfoModel() {
    }

    public CourseInfoModel(String courseName, String courseID, String courseTeacher, String courseClass, String classmateListLink, String classForumLink) {
        CourseName = courseName;
        CourseID = courseID;
        CourseTeacher = courseTeacher;
        CourseClass = courseClass;
        ClassmateListLink = classmateListLink;
        ClassForumLink = classForumLink;
    }

    @Override
    public List<CourseInfoModel> loadFromNet() {
        return null;
    }

    @Override
    public List<CourseInfoModel> loadFromCache() {
        return null;
    }

    @Override
    public boolean cleanCache() {
        return false;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getCourseID() {
        return CourseID;
    }

    public void setCourseID(String courseID) {
        CourseID = courseID;
    }

    public String getCourseTeacher() {
        return CourseTeacher;
    }

    public void setCourseTeacher(String courseTeacher) {
        CourseTeacher = courseTeacher;
    }

    public String getCourseClass() {
        return CourseClass;
    }

    public void setCourseClass(String courseClass) {
        CourseClass = courseClass;
    }

    public String getClassmateListLink() {
        return ClassmateListLink;
    }

    public void setClassmateListLink(String classmateListLink) {
        ClassmateListLink = classmateListLink;
    }

    public String getClassForumLink() {
        return ClassForumLink;
    }

    public void setClassForumLink(String classForumLink) {
        ClassForumLink = classForumLink;
    }
}
