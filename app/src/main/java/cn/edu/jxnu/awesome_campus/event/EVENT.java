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
    public static final int BOOKBORROWED_REFRESH_SUCCESS = 43;
    public static final int BOOKBORROWED_REFRESH_FAILURE = 44;


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

    // Campus ATM
    public static final int CAMPUS_ATM_REFRESH_SUCCESS = 1021;
    public static final int CAMPUS_ATM_REFRESH_FAILURE = 1022;

    // Campus ATM cache
    public static final int CAMPUS_ATM_LOAD_CACHE_SUCCESS = 1023;
    public static final int CAMPUS_ATM_LOAD_CACHE_FAILURE = 1024;
}

