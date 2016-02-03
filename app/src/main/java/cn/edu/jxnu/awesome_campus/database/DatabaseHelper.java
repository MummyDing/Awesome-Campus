package cn.edu.jxnu.awesome_campus.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MummyDing on 16-2-2.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Awesome_Campus";
    private static DatabaseHelper instance = null;
    private static final int DB_VERSION = 1;
    public static final String CLEAR_TABLE_DATA = "delete from ";
    public static final String DROP_TABLE = "drop table if exists ";
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
/*
        *//**
         * Home
         *//*
        db.execSQL(CampusNewsTable.CREATE_TIME);
        db.execSQL(CourseInfoTable.CREATE_TABLE);
        db.execSQL(CourseTable.CREATE_TABLE);


        *//**
         * Leisure
         *//*


        *//**
         * Life
         *//*

        db.execSQL(WeatherInfoTable.CREATE_TABLE);


        *//**
         * Study
         *//*


        *//**
         * Library
         *//*

        db.execSQL(BookBorrowedTable.CREATE_TABLE);
        db.execSQL(BookSearchHistoryTable.CREATE_TABLE);
        db.execSQL(BorrowHistoryTable.CREATE_TIME);

        *//**
         * Education
         *//*

        db.execSQL(CourseScoreTable.CREATE_TABLE);
        db.execSQL(ExamTimeTable.CREATE_TABLE);

        */




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public static synchronized DatabaseHelper instance(Context context){
        if(instance == null){
            instance = new DatabaseHelper(context,DB_NAME,null,DB_VERSION);
        }
        return instance;
    }
}
