package cn.edu.jxnu.awesome_campus.ui.library;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.ui.base.BaseWebViewActivity;

/**
 * Created by KevinWu on 16-4-23.
 */
public class BookSearchDetailsActivity extends BaseWebViewActivity{
    private static final String TAG="BookSearchDetails...";
    private String url;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        url=intent.getStringExtra("URL");
        super.onCreate(savedInstanceState);

    }

    @Override
    protected String getLink() {
        Log.d(TAG,"URL为"+url);
        return url;
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
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }
}
