package cn.edu.jxnu.awesome_campus.dataentity.education;

import cn.edu.jxnu.awesome_campus.dataentity.BaseEntity;

/**
 * Created by KevinWu on 2016/2/3.
 */
public class ExamTimeEntity extends BaseEntity {
    public String courseID;
    public String courseName;
    public String examTime;
    public String examRoom;
    public String examSeat;
    // 备注信息
    private String remark;

    public ExamTimeEntity(int elementNum,String courseID, String courseName, String examTime, String examRoom, String examSeat, String remark) {
        super();
        super.elementNum=elementNum;
        this.courseID = courseID;
        this.courseName = courseName;
        this.examTime = examTime;
        this.examRoom = examRoom;
        this.examSeat = examSeat;
        this.remark = remark;
    }
}
