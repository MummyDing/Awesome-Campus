package cn.edu.jxnu.awesome_campus.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
    protected boolean isShowBigPic=true;
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
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
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
        if(isShowBigPic){
            Log.d("导入图片监听设置","----");
            setBigPicConfig();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
    public void onEventMainThread(EventModel eventModel) {
        onEventComing(eventModel);

    }

    protected abstract void onEventComing(EventModel eventModel);

    protected void setBigPicConfig() {
        webView.addJavascriptInterface(new JavascriptInterface(this),
                "imagelistener");
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                // 监听器加载这是为了防止动态加载图片时新加载的图片无法预览
                addImageClickListener();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                addImageClickListener();
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    // js通信接口
    public class JavascriptInterface {

        private Context context;

        public JavascriptInterface(Context context) {
            this.context = context;
        }

        @android.webkit.JavascriptInterface
        public void openImage(String object, int position) {
            Intent intent = new Intent();
            String resultObj[]=object.split(",");
            Log.d("--","图片地址为"+resultObj[position]);
            Log.d("--","图片位置为"+position);
            intent.putExtra("url", resultObj[position]);
            intent.setClass(context, BaseDialogPhotoView.class);
            context.startActivity(intent);
        }
    }

    // 注入js函数监听
    private void addImageClickListener() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        String imageloadJS = getFromAssets("imageload.js");
        Log.d("--",imageloadJS);
        if (!TextUtils.isEmpty(imageloadJS)) {
            webView.loadUrl(imageloadJS);
        }
    }

    // 读取assets中的文件
    private String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
