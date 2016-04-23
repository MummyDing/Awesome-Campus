package cn.edu.jxnu.awesome_campus.ui.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;

public abstract class BaseWebViewActivity extends SwipeBackActivity {

    protected WebView webView;
    protected ProgressBar progressBar;
    protected boolean isLoading = true;
    protected String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_webview);
        initData();
    }

    protected abstract String getLink();
    protected abstract String getData();
    protected void initData(){

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
                    if (newProgress > 25) {
                        isLoading = false;
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });

        if (TextUtil.isNull(getData())== false){
            webView.loadDataWithBaseURL("file:///android_asset/", getData(), "text/html", "utf-8", null);

        }else if (TextUtil.isNull(getLink()) == false){
            webView.loadUrl(getLink());
        }
    }
}
