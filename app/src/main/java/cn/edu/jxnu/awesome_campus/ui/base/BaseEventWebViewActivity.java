package cn.edu.jxnu.awesome_campus.ui.base;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.IModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsModel;

/**
 * 带Event的网页浏览器activity
 * Created by KevinWu on 16-5-12.
 */
public abstract class BaseEventWebViewActivity extends BaseToolbarActivity{
    protected WebView webView;
    protected ProgressBar progressBar;
    protected boolean isLoading = true;
    protected String data;
    protected String title="";
    protected int layoutID=R.layout.activity_base_webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(layoutID);
        initToolbar();
        setToolbarTitle(title);
        EventBus.getDefault().register(this);
    }

    protected abstract void init();

    //    获取数据
    protected abstract void onDataRefresh();

//    数据显示
    protected  void onDataShow(String css){
        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });


        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (isLoading) {
                    progressBar.incrementProgressBy(newProgress - progressBar.getProgress());
                    if (newProgress > 45) {
                        isLoading = false;
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });
        Log.d("加载网页","---");
        webView.loadDataWithBaseURL("file:///android_asset/"," <link rel=\"stylesheet\" type=\"text/css\" href="+css+" />"+data, "text/html", "utf-8", null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
    public void onEventMainThread(EventModel eventModel) {
        onEventComing(eventModel);

    }

    protected abstract void onEventComing(EventModel eventModel);


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
