package cn.edu.jxnu.awesome_campus.model.common;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;

/**
 * Created by MummyDing on 16-1-29.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public enum DrawerItem {

    HOME(R.string.home, R.mipmap.ic_home_black),
    LEISURE(R.string.leisure, R.mipmap.ic_leisure_black),
    LIFE(R.string.life, R.mipmap.ic_life_black),
    JXNUGO(R.string.jxnugo, R.mipmap.ic_shopping_cart_black),
    LIBRARY(R.string.library, R.mipmap.ic_library_black),
    EDUCATION(R.string.education, R.mipmap.ic_edu_black),
    JOB(R.string.job_jobtitle,R.drawable.ic_people_black_48dp),
    THEME(R.string.theme, R.mipmap.ic_theme_black),
    SETTINGS(R.string.settings, R.mipmap.ic_settings_black),
    ABOUT(R.string.about, R.mipmap.ic_about_black),
    LOGOUT(R.string.logout, R.mipmap.ic_logout_black);

    private String itemName;
    private int itemIconID;
    private int id;

    DrawerItem(int stringID, int itemIconID) {
        this.itemName = InitApp.AppContext.getString(stringID);
        this.itemIconID = itemIconID;
        this.id = stringID;
    }


    public String getItemName() {
        return itemName;
    }

    public int getItemIconID() {
        return itemIconID;
    }

    public int getId() {
        return id;
    }
}
