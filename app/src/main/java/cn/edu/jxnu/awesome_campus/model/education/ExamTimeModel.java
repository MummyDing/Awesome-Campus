package cn.edu.jxnu.awesome_campus.model.education;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.education.ExamTimeDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by MummyDing on 16-1-27.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ExamTimeModel implements IModel<ExamTimeModel> {

    protected ExamTimeDAO examTimeDAO;
    /**
     * 考试安排表
     */

    private String CourseID;
    private String CourseName;
    private String ExamTime;
    private String ExamRoom;
    private String ExamSeat;
    // 备注信息
    private String Remark;

    public ExamTimeModel() {
        examTimeDAO = new ExamTimeDAO();
    }

    public ExamTimeModel(String courseID, String courseName, String examTime, String examRoom, String examSeat, String remark) {
        this();
        CourseID = courseID;
        CourseName = courseName;
        ExamTime = examTime;
        ExamRoom = examRoom;
        ExamSeat = examSeat;
        Remark = remark;
    }


    @Override
    public void loadFromCache() {
        examTimeDAO.loadFromCache();
    }

    @Override
    public void loadFromNet() {
        examTimeDAO.loadFromNet();
    }

    @Override
    public boolean clearCache() {
        return examTimeDAO.clearCache();
    }

    @Override
    public boolean cacheAll(List<ExamTimeModel> list) {
        return examTimeDAO.cacheAll(list);
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

    public String getExamTime() {
        return ExamTime;
    }

    public void setExamTime(String examTime) {
        ExamTime = examTime;
    }

    public String getExamRoom() {
        return ExamRoom;
    }

    public void setExamRoom(String examRoom) {
        ExamRoom = examRoom;
    }

    public String getExamSeat() {
        return ExamSeat;
    }

    public void setExamSeat(String examSeat) {
        ExamSeat = examSeat;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
