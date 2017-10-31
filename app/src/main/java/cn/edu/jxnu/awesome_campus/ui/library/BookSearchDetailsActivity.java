package cn.edu.jxnu.awesome_campus.ui.library;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.ui.base.BaseWebViewActivity;

/**
 * Created by KevinWu on 16-4-23.
 */
public class BookSearchDetailsActivity extends BaseWebViewActivity{
    private static final String TAG="BookSearchDetails...";
    private String url;
    private String title;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        url=intent.getStringExtra("URL");
        title=intent.getStringExtra("TITLE");
        TCAgent.onPageStart(InitApp.AppContext, TAG);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected String getLink() {
//        Log.d(TAG,"URL为"+url);
        return null;
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
        super.title=title;
        return url;
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        TCAgent.onPageEnd(InitApp.AppContext, TAG);
        super.onDestroy();

    }
}
