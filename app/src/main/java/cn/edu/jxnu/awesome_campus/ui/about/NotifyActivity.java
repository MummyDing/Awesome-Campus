package cn.edu.jxnu.awesome_campus.ui.about;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.tendcloud.tenddata.TCAgent;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.ui.base.BaseWebViewActivity;

/**
 * Created by MummyDing on 16-4-23.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class NotifyActivity extends BaseWebViewActivity {
    private static final String TAG="NotifyActivity";

    private String type;
    private String data;
    private String title;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TCAgent.onPageStart(InitApp.AppContext, TAG);
    }

    @Override
    protected String getLink() {
        if (type == null){
            type = getIntent().getStringExtra(getString(R.string.id_type));
            data = getIntent().getStringExtra(getString(R.string.id_data));
            super.title=getIntent().getStringExtra(getString(R.string.id_title));
        }
        if (type.equals(getString(R.string.link)))
        return data;
        return null;
    }

    @Override
    protected String getData() {
        if (type == null){
            type = getIntent().getStringExtra(getString(R.string.id_type));
            data = getIntent().getStringExtra(getString(R.string.id_data));
            super.title=getIntent().getStringExtra(getString(R.string.id_title));
        }
        if (type.equals(getString(R.string.data)))
            return data;
        else if (type.equals(getString(R.string.id_none)))
            return "<h1>"+getString(R.string.no_notify)+"</h1>";
        return null;
    }

    @Override
    protected String getLinkData() {
        if (type == null){
            type = getIntent().getStringExtra(getString(R.string.id_type));
            data = getIntent().getStringExtra(getString(R.string.id_data));
            super.title=getIntent().getStringExtra(getString(R.string.id_title));
        }
        if (type.equals(getString(R.string.link_data)))
            return data;
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
