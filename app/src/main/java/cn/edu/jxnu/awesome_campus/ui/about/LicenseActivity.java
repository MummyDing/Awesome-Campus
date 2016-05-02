package cn.edu.jxnu.awesome_campus.ui.about;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.tendcloud.tenddata.TCAgent;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.ui.base.BaseWebViewActivity;

/**
 * Created by MummyDing on 16-4-24.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class LicenseActivity extends BaseWebViewActivity {
    private static final String TAG="LicenseActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TCAgent.onPageStart(InitApp.AppContext, TAG);
    }

    @Override
    protected String getLink() {
        super.title=getString(R.string.text_license);
        return "file:///android_asset/licenses.html";
    }

    @Override
    protected String getData() {
        return null;
    }

    @Override
    protected String getLinkData() {
        return null;
    }

    @Override
    protected String getLinkParseData() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(InitApp.AppContext, TAG);
    }
}
