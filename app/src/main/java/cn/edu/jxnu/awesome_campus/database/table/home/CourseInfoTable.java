package cn.edu.jxnu.awesome_campus.database.table.home;

/**
 * Created by MummyDing on 16-1-26.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseInfoTable {
    /***
     * 课程信息表
     */
    public static final String NAME = "CourseInfoTable";

    /**
     * 字段部分
     * 主键: 课程名 CourseName
     * 说明: 注销需清空此表 否则新账号登陆课程信息将发生冲突
     */
    public static final String COURSE_NAME = "CourseName";
    public static final String COURSE_ID = "CourseID";
    public static final String COURSE_TEACHER = "CourseTeacher";
    //  课程班级
    public static final String COURSE_CLASS = "CourseClass";
    // 该课程全部同学名单链接
    public static final String CLASSMATE_LIST_LINK = "ClassmateListLink";
    // 该课程"课程讨论区"链接
    public static final String CLASS_FORUM_LINK = "ClassForumLink";


    /**
     * 字段ID 数据库操作建立字段对应关系 从0开始
     */
    public static final int ID_COURSE_NAME = 0;
    public static final int ID_COURSE_ID = 1;
    public static final int ID_COURSE_TEACHER = 2;
    public static final int ID_COURSE_CLASS = 3;
    public static final int ID_CLASSMATE_LIST_LINK = 4;
    public static final int ID_CLASS_FORUM_LINK = 5;


    public static final String CREATE_TABLE = "create table "+NAME+"("+
            COURSE_NAME+" text primary key, "+
            COURSE_ID+" text, "+
            COURSE_TEACHER+" text, "+
            COURSE_CLASS+" text, "+
            CLASSMATE_LIST_LINK+" text, "+
            CLASS_FORUM_LINK+" text)";
}
