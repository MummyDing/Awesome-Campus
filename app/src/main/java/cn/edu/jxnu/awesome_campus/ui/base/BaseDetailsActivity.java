package cn.edu.jxnu.awesome_campus.ui.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.InputStream;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.ImageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TimeUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.InputStreamCallback;
import cn.edu.jxnu.awesome_campus.view.base.BaseView;

/**
 * Created by MummyDing on 16-2-13.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public abstract class BaseDetailsActivity extends SwipeBackActivity implements BaseView {


    protected Toolbar toolbar;
    protected WebView contentView;
    protected SimpleDraweeView topImage;
    protected NestedScrollView scrollView;
    protected FrameLayout mainContent;
    protected ProgressBar progressBar;
    protected ProgressBar progressBarTopPic;
    protected ImageButton networkBtn;
    private static int imgID[]={
            R.drawable.default_news_top_bg_0,
            R.drawable.default_news_top_bg_1,
            R.drawable.default_news_top_bg_2,
            R.drawable.default_news_top_bg_3,
            R.drawable.default_news_top_bg_4,
            R.drawable.default_news_top_bg_5,
            R.drawable.default_news_top_bg_6,
            R.drawable.default_news_top_bg_7,
            R.drawable.default_news_top_bg_8,
            R.drawable.default_news_top_bg_9
    };

    protected abstract void onDataRefresh();
    protected abstract void onEventComing(EventModel eventModel);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        EventBus.getDefault().register(this);
    }

    protected int getLayoutID(){
        return R.layout.activity_campus_details;
    }


    @Override
    public void displayLoading() {
        if(progressBar != null){
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        if(progressBar != null){
            progressBar.setVisibility(View.GONE);
            progressBarTopPic.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void displayNetworkError() {
        if(networkBtn != null){
            networkBtn.setVisibility(View.VISIBLE);
        }
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
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBarTopPic=(ProgressBar) findViewById(R.id.progressBarTopPic);
        networkBtn = (ImageButton) findViewById(R.id.networkBtn);
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
         * 网络异常就显示
         */
        networkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkBtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                onDataRefresh();
            }
        });
        onDataRefresh();
    }



    /**
     * 设置布局背景，其实就是边缘空隙的颜色，颜色取自顶部图片的主色调
     *
     * @param url
     */
    protected void setMainContentBg(String url) {
        if (url == null) {
            setDefaultColor();
            return;
        }
        NetManageUtil.get(url)
                .enqueue(new InputStreamCallback() {
                    @Override
                    public void onSuccess(InputStream result, Headers headers) {
                        final Bitmap bitmap = BitmapFactory.decodeStream(result);
                        if(bitmap == null){
                            setDefaultColor();
                            return;
                        }
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                topImage.setBackground(new BitmapDrawable(getResources(), bitmap));
                                mainContent.setBackgroundColor(ImageUtil.getImageColor(bitmap));
                                progressBarTopPic.setVisibility(View.GONE);
                            }
                        });
                    }

                    @Override
                    public void onFailure(IOException e) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                setDefaultColor();
                            }
                        });
                    }
                });
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
    public void onEventMainThread(EventModel eventModel) {
        onEventComing(eventModel);

    }


    protected void setDefaultColor(){
        int pic_num=(int)(TimeUtil.getTimestamp()%10);
        topImage.setBackground(ContextCompat.getDrawable(this, imgID[pic_num]));
        mainContent.setBackgroundColor(ImageUtil.getImageColor(((BitmapDrawable) topImage.getBackground()).getBitmap()));
        progressBarTopPic.setVisibility(View.GONE);
    }


}
