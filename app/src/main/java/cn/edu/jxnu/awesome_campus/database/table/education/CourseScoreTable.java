package cn.edu.jxnu.awesome_campus.database.table.education;

/**
 * Created by MummyDing on 16-1-26.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseScoreTable {

    /***
     * 考试成绩表
     * 不设主键
     * 说明: 注销需清空此表 否则新账号登陆成绩信息将发生冲突
     */
    public static final String NAME = "CourseScoreTable";

    public static final String TERM = "Term";
    public static final String COURSE_ID = "CourseID";
    public static final String COURSE_NAME = "CourseName";
    // 课程学分
    public static final String COURSE_CREDIT = "CourseCredit";
    // 课程成绩
    public static final String COURSE_SCORE = "CourseScore";
    // 补考成绩
    public static final String AGAIN_SCORE = "AgainScore";
    // 标准分
    public static final String STANDARD_SCORE = "StandardScore";


    /**
     * 字段ID 数据库操作建立字段对应关系 从0开始
     */

    public static final int ID_TERM = 0;
    public static final int ID_COURSE_ID = 1;
    public static final int ID_COURSE_NAME = 2;
    public static final int ID_COURSE_CREDIT = 3;
    public static final int ID_COURSE_SCORE = 4;
    public static final int ID_AGAIN_SCORE = 5;
    public static final int ID_STANDARD_SCORE = 6;



    public static final String CREATE_TABLE = "create table "+NAME+"("+
            TERM+" text, "+
            COURSE_ID+" text, "+
            COURSE_NAME+" text, "+
            COURSE_CREDIT+" text, "+
            COURSE_SCORE+" text, "+
            AGAIN_SCORE+" text, "+
            STANDARD_SCORE+" text)";
}
