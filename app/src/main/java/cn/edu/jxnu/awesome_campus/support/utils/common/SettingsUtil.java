package cn.edu.jxnu.awesome_campus.support.utils.common;

import android.database.sqlite.SQLiteDatabase;
import android.webkit.WebView;

import java.util.Locale;

import javax.crypto.spec.DHGenParameterSpec;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.database.DatabaseHelper;
import cn.edu.jxnu.awesome_campus.database.table.home.CampusNewsTable;
import cn.edu.jxnu.awesome_campus.database.table.leisure.DailyTable;
import cn.edu.jxnu.awesome_campus.database.table.leisure.FilmTable;
import cn.edu.jxnu.awesome_campus.database.table.leisure.ScienceTable;
import cn.edu.jxnu.awesome_campus.support.Settings;

/**
 * Created by MummyDing on 16-4-17.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class SettingsUtil {

    public static int getCurrentLanguage() {
        int lang = Settings.getsInstance().getInt(Settings.LANGUAGE, -1);
        if (lang == -1) {
            String language = Locale.getDefault().getLanguage();
            String country = Locale.getDefault().getCountry();

            if (language.equalsIgnoreCase("zh")) {
                if (country.equalsIgnoreCase("CN")) {
                    lang = 1;
                } else {
                    lang = 2;
                }
            } else {
                lang = 0;
            }
        }
        return lang;
    }

    public static void clearCache(){
        WebView wb = new WebView(InitApp.AppContext);
        wb.clearCache(true);

        DatabaseHelper mHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = mHelper.getWritableDatabase();

        db.execSQL(DatabaseHelper.CLEAR_TABLE_DATA+ CampusNewsTable.NAME);
        db.execSQL(DatabaseHelper.CLEAR_TABLE_DATA+ DailyTable.NAME);
        db.execSQL(DatabaseHelper.CLEAR_TABLE_DATA+ FilmTable.NAME);
        db.execSQL(DatabaseHelper.CLEAR_TABLE_DATA+ ScienceTable.NAME);
    }

}
