package cn.edu.jxnu.awesome_campus.event;

/**
 * Created by MummyDing on 16-2-6.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class EVENT {
    /**
     * Event Code
     */
    // Course Table
    public static final int COURSE_TABLE_REFRESH_SUCCESS = 1;
    public static final int COURSE_TABLE_REFRESH_FAILURE = 2;

    // Course Info
    public static final int COURSE_INFO_REFRESH_SUCCESS = 3;
    public static final int COURSE_INFO_REFRESH_FAILURE = 4;

    // Campus News
    public static final int CAMPUS_NEWS_REFRESH_SUCCESS = 5;
    public static final int CAMPUS_NEWS_REFRESH_FAILURE = 6;

    // Course Score
    public static final int COURSE_SCORE_REFRESH_SUCCESS = 7;
    public static final int COURSE_SCORE_REFRESH_FAILURE = 8;


    // Exam Time
    public static final int COURSE_TIME_REFRESH_SUCCESS = 9;
    public static final int COURSE_TIME_REFRESH_FAILURE = 10;

    // Book Borrowed
    public static final int BOOK_BORROWED_REFRESH_SUCCESS = 11;
    public static final int BOOK_BORROWED_REFRESH_FAILURE = 12;

    //Borrow History
    public static final int BORROWED_HISTORY_REFRESH_SUCCESS = 13;
    public static final int BORROWED_HISTORY_REFRESH_FAILURE = 14;

    //Weather Info
    public static final int WEATHER_INFO_REFRESH_SUCCESS = 15;
    public static final int WEATHER_INFO_REFRESH_FAILURE = 16;

    // Daily
    public static final int DAILY_REFRESH_SUCCESS = 17;
    public static final int DAILY_REFRESH_FAILURE = 18;

    //Daily Detail
    public static final int DAILY_DETAIL_SUCCESS = 19;
    public static final int DAILY_DETAIL_FAILURE = 20;


    // send common Model Detail
    public static final int SEND_MODEL_DETAIL = 21;

    // Campus News Details
    public static final int CAMPUS_NEWS_DETAILS_REFRESH_SUCCESS = 22;
    public static final int CAMPUS_NEWS_DETAILS_REFRESH_FAILURE = 23;

    // Science
    public static final int SCIENCE_REFRESH_SUCCESS = 24;
    public static final int SCIENCE_REFRESH_FAILURE = 25;

    // Science Details
    public static final int SCIENCE_DETAILS_REFRESH_SUCCESS = 26;
    public static final int SCIENCE_DETAILS_REFRESH_FAILURE = 27;

    // Education Login
    public static final int EDUCATION_LOGIN_SUCCESS = 28;
    public static final int EDUCATION_LOGIN_FAILURE_NO_ID = 29;
    public static final int EDUCATION_LOGIN_FAILURE_PASSWORD_INCORRECT=30;
    public static final int EDUCATION_LOGIN_FAILURE_NULL_INPUT=31;
    public static final int EDUCATION_LOGIN_FAILURE_NETWORK_ERROR=32;
    public static final int EDUCATION_LOGIN_SERVER_ERROR = 4433;


    // Exam Time
    public static final int EXAM_TIME_REFRESH_SUCCESS = 33;
    public static final int EXAM_TIME_REFRESH_FAILURE = 34;

    // Film
    public static final int FILM_REFRESH_SUCCESS = 35;
    public static final int FILM_REFRESH_FAILURE = 36;

    // Film Details
    public static final int FILM_DETAILS_REFRESH_SUCCESS = 37;
    public static final int FILM_DETAILS_REFRESH_FAILURE = 38;

    //Library Login
    public static final int LIBRARY_LOGIN_SUCCESS=39;
    public static final int LIBRARY_LOGIN_FAILURE=40;
    public static final int LIBRARY_LOGIN_FAILURE_NETWORKERROR=41;
    public static final int LIBRARY__LOGIN_FAILURE_NULL_INPUT=42;



    //BookBorrowed
//    public static final int BOOKBORROWED_REFRESH_SUCCESS = 43;
//    public static final int BOOKBORROWED_REFRESH_FAILURE = 44;


    //search result
    public static final int BOOK_SEARCH_REFRESH_SUCCESS = 45;
    public static final int BOOK_SEARCH_REFRESH_FAILURE = 46;

    // Campus Express
    public static final int CAMPUS_EXPRESS_SUCCESS = 47;
    public static final int CAMPUS_EXPRESS_FAILURE = 48;

    //SelfStudyRoom Login
    public static final int SELFSTUDYROOM_LOGIN_SUCCESS=49;
    public static final int SELFSTUDYROOM_LOGIN_FAILURE=50;
    public static final int SELFSTUDYROOM_LOGIN_FAILURE_NETWORKERROR=51;
    public static final int SELFSTUDYROOM_LOGIN_FAILURE_NULL_INPUT=52;


    //hot search result
    public static final int HOT_SEARCH_REFRESH_SUCCESS = 53;
    public static final int HOT_SEARCH_REFRESH_FAILURE = 54;

    // Campus ATM
    public static final int CAMPUS_ATM_REFRESH_SUCCESS = 55;
    public static final int CAMPUS_ATM_REFRESH_FAILURE = 56;

    // Notify
    public static final int NOTIFY_REFRESH_FAILURE = 57;
    public static final int NOTIFY_REFRESH_SUCCESS = 58;


    // Self Study Seats
    public static final int SELF_STUDY_SEATS_REFRESH_SUCCESS = 59;
    public static final int SELF_STUDY_SEATS_REFRESH_FAILURE = 60;



    //图书续借code
    public static final int LIBRARY_RENEW_SUCCESS=63;
    public static final int LIBRARY_RENEW_FAILURE=64;

    //商品列表加载code
    public static final int GOODS_LIST_REFRESH_SUCCESS =65;
    public static final int GOODS_LIST_REFRESH_FAILURE =66;


    //商品详情跳转
    public static final int GOODS_DETAIL_INTENT=67;

    //商品评论区
    public static final int GOODS_COMMENT_REFRESH_SUCCESS=68;
    public static final int GOODS_COMMENT_REFRESH_FAILURE=69;

    //商品列表加载下一页
    public static final int GOODS_LIST_NEXTPAGE_REFRESH_SUCCESS =74;
    public static final int GOODS_LIST_NEXTPAGE_REFRESH_FAILURE =75;
    //上传图片
    public static final int GOODS_IMAGES_UPLOAD_SUCCESS=70;
    public static final int GOODS_IMAGES_UPLOAD_FAIL=71;

    //上传帖子
    public static final int POST_UPLOAD_SUCCESS=72;
    public static final int POST_UPLOAD_FAIL=73;

    //帖子评论
    public static final int POST_COMMENT_SUCCESS=76;
    public static final int POST_COMMENT_FAILURE=77;
    public static final int COMMENT_TRIGGER=78;//触发评论

    //收藏帖子返回的状态码
    public static final int POST_COLLECT_SUCCESS=79;
    public static final int POST_COLLECT_FAILURE=80;

    //判断帖子是否已收藏返回的状态码
    public static final int JUDGE_COLLECT_TRUE=81;//已收藏
    public static final int JUDGE_COLLECT_FALSE=82;//未收藏

    //JXNUGO登录的状态码
    public static final int JXNUGO_LOGIN_SUCCESS=83;
    public static final int JXNUGO_LOGIN_FAILURE=84;

    //跳转到已经登录了的用户个人信息
    public static final int JUMP_TO_LOGIN_JXNUGO_USERINFO=85;

    /**
     * Cache Event Code
     */

    // Course Table
    public static final int COURSE_TABLE_LOAD_CACHE_SUCCESS = 1000;
    public static final int COURSE_TABLE_LOAD_CACHE_FAILURE = 1001;

    // Course Info
    public static final int COURSE_INFO_LOAD_CACHE_SUCCESS = 1002;
    public static final int COURSE_INFO_LOAD_CACHE_FAILURE = 1003;

    // CampusNews
    public static final int CAMPUS_NEWS_LOAD_CACHE_SUCCESS = 1004;
    public static final int CAMPUS_NEWS_LOAD_CACHE_FAILURE = 1005;


    // Daily
    public static final int DAILY_LOAD_CACHE_SUCCESS = 1006;
    public static final int DAILY_LOAD_CACHE_FAILURE = 1007;

    // Film
    public static final int FILM_LOAD_CACHE_SUCCESS = 1008;
    public static final int FILM_LOAD_CACHE_FAILURE = 1009;

    // Science
    public static final int SCIENCE_LOAD_CACHE_SUCCESS = 1010;
    public static final int SCIENCE_LOAD_CACHE_FAILURE = 1011;

    // Campus Express
    public static final int CAMPUS_EXPRESS_LOAD_CACHE_SUCCESS = 1012;
    public static final int CAMPUS_EXPRESS_LOAD_CACHE_FAILURE = 1013;

    // Course Score
    public static final int COURSE_SCORE_LOAD_CACHE_SUCCESS = 1014;
    public static final int COURSE_SCORE_LOAD_CACHE_FAILURE = 1015;

    // Exam Time
    public static final int EXAM_TIME_LOAD_CACHE_SUCCESS = 1016;
    public static final int EXAM_TIME_LOAD_CACHE_FAILURE = 1017;

    // Book Borrowed
    public static final int BOOK_BORROWED_LOAD_CACHE_SUCCESS = 1018;
    public static final int BOOK_BORROWED_LOAD_CACHE_FAILURE = 1019;

    // Book Result
    public static final int BOOK_RESULT_DETAILS = 1020;

    //Notify
    public static final int NOTIFY_LOAD_CACHE_SUCCESS = 1021;
    public static final int NOTIFY_LOAD_CACHE_FAILURE = 1022;

    // Campus ATM cache
    public static final int CAMPUS_ATM_LOAD_CACHE_SUCCESS = 1023;
    public static final int CAMPUS_ATM_LOAD_CACHE_FAILURE = 1024;

    //hot search result
    public static final int HOT_SEARCH_LOAD_CACHE_SUCCESS = 1025;
    public static final int HOT_SEARCH_LOAD_CACHE_FAILURE = 1026;

    // Self Study Seats
    public static final int SELF_STUDY_SEATS_LOAD_CACHE_SUCCESS = 1027;
    public static final int SELF_STUDY_SEATS_LOAD_CACHE_FAILURE = 1028;

    // 天气
    public static final int WEATHER_INFO_LOAD_CACHE_SUCCESS = 1029;
    public static final int WEATHER_INFO_LOAD_CACHE_FAILURE = 2030;



    //Jxnugo uerinfo
    public  static final int JXNUGO_USERINFO_LOAD_USER=1031;
    public  static final int JXNUGO_USERINFO_LOAD_USER_SUCCESS=1032;
    public static final int JXNUGO_USERINFO_LOAD_USER_FALURE=1033;
    public static final int JXNUGO_LOAD_PEOPLELIST_SUCCESS=1034;
    public static final int JXNUGO_LOAD_PEOPLELIST_FAILURE=1035;

    public  static final  int JXNUGO_FOLLOW_SUCCESS=1036;
    public static  final  int JXNUGO_FOLLOW_FAILURE=1037;
    public  static  final  int JXNUGO_UNFOLLOW_SUCCESS=1038;
    public static final int JXNUGO_UNFOLLOW_FAILURE=1039;
    public  static final int JXNUGO_USERINFO_LOAD_LOGIN_USER=1040;


    //Common


    public static final int UPDATE_MENU = 2000;

    public static final int UPDATE_SELECTED_MENU_TO_HOME = 2001;

    public static final int SWIPE_TO_LIBRARY_LOGIN = 2002;

    // Jump to Login
    public static final int JUMP_TO_EDUCATION_LOGIN = 2003;
    // Jump to Main Activity
    public static final int JUMP_TO_MAIN = 2004;

    public static final int JUMP_TO_LIBRARY_LOGIN = 2005;

    public static final int JUMP_TO_LIBRARY_BORROWED = 2006;

    public static final int SWIPE_TO_LIBRARY_BORROWED = 2007;

    public static final int NO_COURSE = 2008;

    public static final int HAVE_COURSE = 2009;

    //Jump to jxnugo userinfo
    public  static final  int JUMP_TO_JXNUGO_USERINFO=2010;


    public static final int JUMP_TO_JXNUGO=2011;

    public  static final int JUMP_TO_JXNUGO_LOAD_POEPLE=2012;
}

