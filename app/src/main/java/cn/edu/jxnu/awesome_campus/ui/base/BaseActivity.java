package cn.edu.jxnu.awesome_campus.ui.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import cn.edu.jxnu.awesome_campus.Config;
import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.support.theme.ThemeConfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.SettingsUtil;

/**
 * Created by MummyDing on 16-1-29.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class BaseActivity extends AppCompatActivity {
    private int mLang = -1;
    protected void loadConfig() {
        SPUtil sp=new SPUtil(InitApp.AppContext);
        Config.themeSelected=sp.getIntSP(Config.SP_FILE_NAME,Config.THEME_SELECTED);
        this.setTheme(ThemeConfig.themeStyle[Config.themeSelected]);
        // Language
        mLang = SettingsUtil.getCurrentLanguage();
        if (mLang > -1) {
            SettingsUtil.changeLanguage(this, mLang);
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        loadConfig();
        super.onCreate(savedInstanceState);

    }
}
