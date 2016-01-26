package cn.edu.jxnu.awesome_campus.database.table.education;

/**
 * Created by MummyDing on 16-1-26.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ExamTimeTable {
    /***
     * 考试安排表
     */
    public static final String NAME = "ExamTimeTable";

    /**
     * 考试安排表
     * 不设主键
     * 说明: 注销需清空此表 否则新账号登陆考试安排信息将发生冲突
     */

    public static final String COURSE_ID = "CourseID";
    public static final String COURSE_NAME = "CourseName";
    public static final String EXAM_TIME = "ExamTime";
    public static final String EXAM_ROOM = "ExamRoom";
    public static final String EXAM_SEAT = "ExamSeat";
    // 备注信息
    public static final String REMARK = "Remark";

    /**
     * 字段ID 数据库操作建立字段对应关系 从0开始
     */

    public static final int ID_COURSE_ID = 0;
    public static final int ID_COURSE_NAME =1;
    public static final int ID_EXAM_TIME = 2;
    public static final int ID_EXAM_ROOM = 3;
    public static final int ID_EXAM_SEAT = 4;
    public static final int ID_REMARK = 5;

    public static final String CREATE_TABLE = "create table "+NAME+"("+
            COURSE_ID+" text, "+
            COURSE_NAME+" text, "+
            EXAM_TIME+" text, "+
            EXAM_ROOM+" text, "+
            EXAM_SEAT+" text, "+
            REMARK+" text)";



}
