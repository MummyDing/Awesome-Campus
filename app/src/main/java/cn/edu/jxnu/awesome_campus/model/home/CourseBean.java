package cn.edu.jxnu.awesome_campus.model.home;

/**
 * Created by MummyDing on 16-2-15.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseBean {
    private int courseOfDay;
    private String courseName;
    private String courseRoom;


    public CourseBean(int courseOfDay, String courseName, String courseRoom) {
        this.courseOfDay = courseOfDay;
        this.courseName = courseName;
        this.courseRoom = courseRoom;
    }

    public int getCourseOfDay() {
        return courseOfDay;
    }

    public void setCourseOfDay(int courseOfDay) {
        this.courseOfDay = courseOfDay;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseRoom() {
        return courseRoom;
    }

    public void setCourseRoom(String courseRoom) {
        this.courseRoom = courseRoom;
    }
}
