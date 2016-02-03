package cn.edu.jxnu.awesome_campus.dataentity.education;


import cn.edu.jxnu.awesome_campus.dataentity.BaseEntity;

/**
 * Created by KevinWu on 2016/2/3.
 */
public class CourseScoreEntity extends BaseEntity{
    /***
     * 考试成绩数据实体
     */
    public String term;
    public String courseID;
    public String courseName;
    // 课程学分
    public String courseCredit;
    // 课程成绩
    public String courseScore;
    // 补考成绩
    public String againScore;
    // 标准分
    public String standardScore;

    public CourseScoreEntity(int elementNum,String term,String courseID,String courseName,String courseCredit,String courseScore,String againScore,String standardScore){
        super.elementNum=elementNum;
        this.term=term;
        this.courseID=courseID;
        this.courseName=courseName;
        this.courseCredit=courseCredit;
        this.courseScore=courseScore;
        this.againScore=againScore;
        this.standardScore=standardScore;
    }
}