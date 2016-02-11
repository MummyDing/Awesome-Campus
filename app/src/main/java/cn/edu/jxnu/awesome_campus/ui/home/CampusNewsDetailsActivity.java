package cn.edu.jxnu.awesome_campus.ui.home;


import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.home.CampusNewsModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.CampusNewsContentParse;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.ImageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
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
    private CampusNewsModel model;
    private SimpleDraweeView topImage;
    private NestedScrollView scrollView;
    private FrameLayout mainContent;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_news_details);
        EventBus.getDefault().register(this);
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

        //对toolbar进行下移
        int height = DisplayUtil.getScreenHeight(InitApp.AppContext);
        LinearLayout ll = (LinearLayout) findViewById(R.id.stbar);
        LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) ll.getLayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            llp.height = (int) (height * 0.03);
            ll.setLayoutParams(llp);
        }


        mainContent = (FrameLayout) findViewById(R.id.main_content);
        scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        topImage = (SimpleDraweeView) findViewById(R.id.topImage);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.top_gradient));
        contentView = (WebView) findViewById(R.id.content_view);
        contentView.getSettings().setJavaScriptEnabled(true);

        /**
         * 根据主色调设置背景色
         */
        setMainContentBg();
        if(TextUtil.isNull(model.getNewsPicURL()) == false){
            topImage.setImageURI(Uri.parse(model.getNewsPicURL()));
            for(int i=1 ; i<= 5 ; i+=2) {
                handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setMainContentBg();
                    }
                }, i*1000);
            }
        }
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                topImage.setTranslationY(Math.max(-scrollY / 2, -DisplayUtil.dip2px(getBaseContext(), 170)));
            }
        });

        String data = importStr(); //这里放html代码
        CampusNewsContentParse myParse = new CampusNewsContentParse(data);
        data = myParse.getEndStr();
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


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe (threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
    public void onEventMainThread(EventModel eventModel){
        if(eventModel.getEventCode() == EVENT.SEND_MODEL_DETAIL) {
            model = (CampusNewsModel) eventModel.getData();
            initView();
        }
    }

    private void setMainContentBg(){
        mainContent.setBackgroundColor(ImageUtil.getImageColor(((BitmapDrawable)topImage.getBackground()).getBitmap()));
    }


}
