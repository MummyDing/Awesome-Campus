package cn.edu.jxnu.awesome_campus.support.config;

/**
 * 记录url
 * Created by KevinWu on 2016/2/7.
 */
public class Urlconfig {
    //简书基础url
    public static final String JianShu_Base_URL="http://www.jianshu.com";
    //简书基础list的url
    public static final String JianShu_List_URL= "http://www.jianshu.com/c/1hjajt";
//    public static final String JianShu_List_URL="http://www.jianshu.com/collections/12142/notes?order_by=added_at&page=1";
    //教务在线基础url
    public static final String Education_Classmate_Base_URL="http://jwc.jxnu.edu.cn/MyControl/All_Display.aspx?UserControl=";
    //教务在线课程讨论区基础url
    public static final String Education_CourseForum_Base_URL="http://jwc.jxnu.edu.cn/MyControl/";

    //教务在线登录的url
    public static final String Education_Login_URL="http://jwc.jxnu.edu.cn/Default_Login.aspx?preurl=";
    // 课程表URL
    public static final String CourseTable_URL="http://jwc.jxnu.edu.cn/User/default.aspx?&&code=111&uctl=MyControl%5cxfz_kcb.ascx&MyAction=Personal";
    //课程表请求参数获取
    public static final String CourseTablePostParams_URL = "https://coding.net/u/MummyDing/p/Awesome-Campus-Data/git/raw/master/config/CourseTableRequestConfig.json";
    //课程信息url（其实和课程表一样）
    public static final String CourseInfo_URL  ="http://jwc.jxnu.edu.cn/User/default.aspx?&&code=111&uctl=MyControl%5cxfz_kcb.ascx&MyAction=Personal";
    //课程成绩的基础url
    public static final String CourseScore_URL="http://jwc.jxnu.edu.cn/MyControl/All_Display.aspx?UserControl=xfz_cj.ascx&Action=Personal";
    //考试安排获取url
    public static final String ExamTime_URL="http://jwc.jxnu.edu.cn/User/default.aspx?&code=129&&uctl=MyControl%5cxfz_test_schedule.ascx";


    //江西师大新闻网基础URL
    public static final String CampusNews_Base_URL="http://news.jxnu.edu.cn";
    //校内新闻（校内要闻）的url
    public static final String CampusNews_YW_URL="http://news.jxnu.edu.cn/s/271/t/910/p/12/list.htm";
    //校内新闻（媒体师大）的url
    public static final String CampusNews_MT_URL="http://news.jxnu.edu.cn/s/271/t/910/p/16/list.htm";
    //校内新闻（校园动态）的url
    public static final String CampusNews_DT_URL="http://news.jxnu.edu.cn/s/271/t/910/p/17/list.htm";



    //图书馆登录url
    //public static final String Library_Login_URL="http://219.229.250.138:8080/reader/redr_verify.php";
    public static final String Library_Login_URL="http://tsg.jxnu.edu.cn:8080/reader/redr_verify.php";

    //图书馆重定向url
    //public static final String Library_Redirect_URL="http://219.229.250.138:8080/reader/redr_cust_result.php";
    public static final String Library_Redirect_URL="http://tsg.jxnu.edu.cn:8080/reader/redr_cust_result.php";

    //图书馆借书url
    public static final String Library_Book_Borrowed_URL="http://tsg.jxnu.edu.cn:8080/reader/book_lst.php";

    //标签云url
    public static final String Library_Book_HOT_Search="http://tsg.jxnu.edu.cn:8080/opac/top100.php";
    //图书馆藏详情url
    //使用时将part1和part2接起来，中间是图书的url信息
    public static final String Library_Book_Search_DETAIL_URL_PART1="http://tsg.jxnu.edu.cn:8090/search?d=http%3a%2f%2f192.168.210.110%3a8080%2fopac%2fitem.php";
    public static final String Library_Book_Search_DETAIL_URL_PART2="&xc=4";
    //图书馆搜索url
    public static final String Library_Book_Search_URL="http://tsg.jxnu.edu.cn:8080/opac/openlink.php";

    //图书馆续借url
    public static final String Library_Book_Renew_URL="http://tsg.jxnu.edu.cn:8080/reader/ajax_renew.php?";

    //自习室登录url
    public static final String SelfStudyRoom_Login_URL="http://zwfp.jxnu.jadl.net/Login.aspx";

    //自习室重定向url
    public static final String SelfStudyRoom_Redirect_URL="http://zwfp.jxnu.jadl.net/MainFunctionPage.aspx";

    //自习室剩余座位查询url
    public static final String SelfStudyRoom_Seat_Left_URL="http://zwfp.jxnu.jadl.net/ReadingRoomInfos/ReadingRoomState.aspx";


    //应用配置参数请求url
    //课程表请求参数获取
    public static final String AppConfig_URL = "https://coding.net/u/MummyDing/p/Awesome-Campus-Data/git/raw/master/config/Config.json";
}
