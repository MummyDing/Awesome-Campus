package cn.edu.jxnu.awesome_campus.ui.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.edu.jxnu.awesome_campus.R;

/**
 * Created by MummyDing on 16-1-29.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class BaseActivity extends AppCompatActivity {

    protected void loadConfig(){
        this.setTheme(R.style.LightgreenTheme);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadConfig();
    }
}
