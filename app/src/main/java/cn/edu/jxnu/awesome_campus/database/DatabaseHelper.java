package cn.edu.jxnu.awesome_campus.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.database.table.about.NotifyTable;
import cn.edu.jxnu.awesome_campus.database.table.education.CourseScoreTable;
import cn.edu.jxnu.awesome_campus.database.table.education.ExamTimeTable;
import cn.edu.jxnu.awesome_campus.database.table.home.CampusNewsTable;
import cn.edu.jxnu.awesome_campus.database.table.home.CourseInfoTable;
import cn.edu.jxnu.awesome_campus.database.table.home.CourseTable;
import cn.edu.jxnu.awesome_campus.database.table.leisure.DailyTable;
import cn.edu.jxnu.awesome_campus.database.table.leisure.FilmTable;
import cn.edu.jxnu.awesome_campus.database.table.leisure.ScienceTable;
import cn.edu.jxnu.awesome_campus.database.table.library.BookBorrowedTable;
import cn.edu.jxnu.awesome_campus.database.table.library.BookSearchHistoryTable;
import cn.edu.jxnu.awesome_campus.database.table.library.BorrowHistoryTable;
import cn.edu.jxnu.awesome_campus.database.table.library.HotSearchTable;
import cn.edu.jxnu.awesome_campus.database.table.life.CampusATMTable;
import cn.edu.jxnu.awesome_campus.database.table.life.CampusExpressTable;
import cn.edu.jxnu.awesome_campus.database.table.life.WeatherInfoTable;

/**
 * Created by MummyDing on 16-2-2.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Awesome_Campus";
    private static DatabaseHelper instance = null;
    private static SQLiteDatabase db = null;
    private static final int DB_VERSION = 2;//数据库版本号
    public static final String CLEAR_TABLE_DATA = "delete from ";
    public static final String DROP_TABLE = "drop table if exists ";
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
         * Home
         */
        db.execSQL(CampusNewsTable.CREATE_TABLE);
        db.execSQL(CourseInfoTable.CREATE_TABLE);
        db.execSQL(CourseTable.CREATE_TABLE);


        /*
         * Leisure
         */
        db.execSQL(DailyTable.CREATE_TABLE);
        db.execSQL(FilmTable.CREATE_TABLE);
        db.execSQL(ScienceTable.CREATE_TABLE);

        /*
         * Life
                */

        db.execSQL(WeatherInfoTable.CREATE_TABLE);
        db.execSQL(CampusExpressTable.CREATE_TABLE);
        db.execSQL(CampusATMTable.CREATE_TABLE);

        /*
         * Study
         */


        /*
         * Library
         */

        db.execSQL(BookBorrowedTable.CREATE_TABLE);
        db.execSQL(BookSearchHistoryTable.CREATE_TABLE);
        db.execSQL(BorrowHistoryTable.CREATE_TABLE);
        db.execSQL(HotSearchTable.CREATE_TIME);

        /*
         * Education
         */

        db.execSQL(CourseScoreTable.CREATE_TABLE);
        db.execSQL(ExamTimeTable.CREATE_TABLE);

        /**
         * Notify
         */
        db.execSQL(NotifyTable.CREATE_TABLE);




    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2){
            db.execSQL(DROP_TABLE+NotifyTable.NAME);
            db.execSQL(NotifyTable.CREATE_TABLE);
            db.execSQL(DROP_TABLE+WeatherInfoTable.NAME);
            db.execSQL(WeatherInfoTable.CREATE_TABLE);
            db.execSQL(DROP_TABLE+CourseTable.NAME);
            db.execSQL(CourseTable.CREATE_TABLE);
        }
    }
    public static synchronized DatabaseHelper getInstance(){
        if(instance == null){
            instance = new DatabaseHelper(InitApp.AppContext,DB_NAME,null,DB_VERSION);
        }
        return instance;
    }

    public static synchronized SQLiteDatabase getDatabase(){
        if (db == null){
            db = getInstance().getWritableDatabase();
        }
        return db;
    }


    public static void exeSQL(String sql,String ... params){
        getDatabase().execSQL(sql,params);
    }

    public static void exeSQL(String sql){
        getDatabase().execSQL(sql);
    }


    public static void insert(String table, ContentValues cv){
        getDatabase().insert(table,null,cv);
    }


    public static Cursor selectAll(String table){
        return getDatabase().query(table,null,null,null,null,null,null);
    }

    public static void clearTable(String table){
        exeSQL(CLEAR_TABLE_DATA+table);
    }

    public static void clearUserData(){
        clearTable(CourseTable.NAME);
        clearTable(CourseInfoTable.NAME);
        clearTable(CourseScoreTable.NAME);
        clearTable(ExamTimeTable.NAME);
    }
    public static void clearLibraryData(){
        clearTable(BookBorrowedTable.NAME);
    }

}
