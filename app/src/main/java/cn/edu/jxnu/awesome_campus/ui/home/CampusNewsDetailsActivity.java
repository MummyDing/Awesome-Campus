package cn.edu.jxnu.awesome_campus.ui.home;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.okhttp.Headers;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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
import cn.edu.jxnu.awesome_campus.support.utils.html.GetNewsFirstPic;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.InputStreamCallback;
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

        /***
         测试代码
         */
        String data = importStr(); //这里放html代码
        CampusNewsContentParse myParse = new CampusNewsContentParse(data);
        data = myParse.getEndStr();

        //测试取第一张图片url
        String myPicUrl= GetNewsFirstPic.getPicURL(data);
        if(myPicUrl!=null)
            Log.d("取得第一张url为：",myPicUrl);

        /**
         * 根据主色调设置背景色
         */
        setMainContentBg(myPicUrl);

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                topImage.setTranslationY(Math.max(-scrollY / 2, -DisplayUtil.dip2px(getBaseContext(), 170)));
            }
        });
        
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
    public void onEventMainThread(EventModel eventModel){
        if(eventModel.getEventCode() == EVENT.SEND_MODEL_DETAIL) {
            model = (CampusNewsModel) eventModel.getData();
            initView();
        }
    }

    private void setMainContentBg(String url){

        NetManageUtil.get(url)
                .enqueue(new InputStreamCallback() {
                    @Override
                    public void onSuccess(InputStream result, Headers headers) {
                        final Bitmap bitmap = BitmapFactory.decodeStream(result);
                       new Handler(Looper.getMainLooper()).post(new Runnable() {
                           @Override
                           public void run() {
                               topImage.setBackground(new BitmapDrawable(getResources(),bitmap));
                               mainContent.setBackgroundColor(ImageUtil.getImageColor(bitmap));
                           }
                       });
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                });
    }


}
