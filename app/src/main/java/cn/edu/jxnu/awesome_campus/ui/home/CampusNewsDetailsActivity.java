package cn.edu.jxnu.awesome_campus.ui.home;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.support.htmlparse.CampusNewsContentParse;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.ui.base.SwipeBackActivity;
import cn.edu.jxnu.awesome_campus.view.base.BaseView;


/**
 * Created by MummyDing on 16-2-10.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */

public class CampusNewsDetailsActivity extends SwipeBackActivity implements BaseView {

    private Toolbar toolbar;
    private WebView contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_news_details);
        initView();
    }

    @Override
    public void displayLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void displayNetworkError() {

    }

    @Override
    public void initView() {
        /**
         * 测试用 非正式代码 ---By MummyDing
         */
        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        final CardView cardView = (CardView) findViewById(R.id.content_card);
        final ImageView imageView = (ImageView) findViewById(R.id.image);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.top_gradient));
        contentView = (WebView) findViewById(R.id.content_view);
        contentView.getSettings().setJavaScriptEnabled(true);



        contentView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                contentView.loadUrl("javascript:MyApp.resize(document.body.getBoundingClientRect().height)");
                contentView.loadUrl("javascript:document.body.style.margin=\"7%\"; void 0");
                super.onPageFinished(view, url);
            }
        });
        contentView.addJavascriptInterface(this, "MyApp");



        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.d("layout: ",scrollX+" "+scrollY+" "+oldScrollX+" "+oldScrollY+" scroll "+oldScrollY+" "+scrollY);
                imageView.setTranslationY(Math.max(-scrollY/2,-DisplayUtil.dip2px(getBaseContext(),170)));
            }
        });

        String data = importStr(); //这里放html代码
        CampusNewsContentParse myParse=new CampusNewsContentParse(data);
        data=myParse.getEndStr();
        contentView.loadDataWithBaseURL("file:///android_asset/", "<link rel=\"stylesheet\" type=\"text/css\" href=\"CampusNews.css\" />" + data, "text/html", "utf-8", null);
    }

    /**
     * 测试用：导入数据
     *
     * @author KevinWu
     * create at 2016/2/10 18:20
     */
    private String importStr() {
        InputStreamReader inputReader = null;
        try {
            inputReader = new InputStreamReader(getResources().getAssets().open("html_test.txt"));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String result = "";
            while ((line = bufReader.readLine()) != null)
                result += line;
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @JavascriptInterface
    public void resize(final float height) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                contentView.setLayoutParams(new LinearLayout.LayoutParams(DisplayUtil.getScreenWidth(getBaseContext()) - DisplayUtil.dip2px(getBaseContext(),20), (int) (height * getResources().getDisplayMetrics().density)));
            }
        });
    }
}
