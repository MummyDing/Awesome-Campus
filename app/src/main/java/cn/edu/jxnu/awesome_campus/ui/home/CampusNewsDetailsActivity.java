package cn.edu.jxnu.awesome_campus.ui.home;


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
import android.util.Log;
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
import cn.edu.jxnu.awesome_campus.support.urlconfig.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.ImageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.html.GetNewsFirstPic;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.InputStreamCallback;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;
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
    private ProgressBar progressBar;
    private ImageButton networkBtn;

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
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
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
                if (networkBtn.getVisibility() == View.VISIBLE) {
                    networkBtn.setVisibility(View.GONE);
                    getNewsDetails();
                }
            }
        });
        /***
         测试代码
         */
//        String data = importStr(); //这里放html代码


        // 使用方法 getNewsDetails() 在后边定义了 请求html代码
        // 请求前判断之前是否已经有了，有了就不用请求了: if TextUtil.isNull(model.getNewsDetails()) return;
        // 请求到数据后就 model.setNewsDetails("解析后的数据");
        //  使用EventBus发送消息 在onMainEvent中接收到消息后处理ui，比如 progressbar隐藏 加载数据
        // Event Code 都已经定义好了  注意EventModel 这个泛型类型不要写错了！！！ 这里就就是一个String
        //        progressBar.setVisibility(View.GONE);


//        data = myParse.getEndStr();

        //  这个也放到getNewsDetails里边 获取后model.setNewsPicURL(); 不用判断是否null了，这个我用的时候有判断的
        //测试取第一张图片url
//        String myPicUrl= GetNewsFirstPic.getPicURL(data);
//        if(myPicUrl!=null)
//            Log.d("取得第一张url为：",myPicUrl);

        /**
         * 根据主色调设置背景色
         */
//        setMainContentBg(model.getNewsPicURL());
        getNewsDetails();
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

    private void getNewsDetails() {
        NetManageUtil.get(Urlconfig.CampusNews_Base_URL + model.getNewsURL())
                .addTag("")
                .enqueue(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Headers headers) {
                        CampusNewsContentParse myParse = new CampusNewsContentParse(result);
                        model.setNewsPicURL(GetNewsFirstPic.getPicURL(myParse.getEndStr()));
                        model.setNewsDetails(myParse.getEndStr());
                        if (model.getNewsPicURL() != null)
                            Log.d("获取到的图片地址", model.getNewsPicURL());
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                onEventMainThread(new EventModel<CampusNewsModel>(EVENT.CAMPUS_NEWS_DETAILS_REFRESH_SUCCESS));
                            }
                        });
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.e("错误1", "11");
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                onEventMainThread(new EventModel<CampusNewsModel>(EVENT.CAMPUS_NEWS_DETAILS_REFRESH_FAILURE));
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

        switch (eventModel.getEventCode()) {
            case EVENT.SEND_MODEL_DETAIL:
                if (eventModel.getEventCode() == EVENT.SEND_MODEL_DETAIL) {
                    model = (CampusNewsModel) eventModel.getData();
                    initView();
                }
                break;
            case EVENT.CAMPUS_NEWS_DETAILS_REFRESH_SUCCESS:
                scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        topImage.setTranslationY(Math.max(-scrollY / 2, -DisplayUtil.dip2px(getBaseContext(), 170)));
                    }
                });
                contentView.loadDataWithBaseURL("file:///android_asset/", "<link rel=\"stylesheet\" type=\"text/css\" href=\"MyCss.css\" />" + model.getNewsDetails(), "text/html", "utf-8", null);
                setMainContentBg(model.getNewsPicURL());
                progressBar.setVisibility(View.GONE);
                break;
            case EVENT.CAMPUS_NEWS_DETAILS_REFRESH_FAILURE:
                progressBar.setVisibility(View.GONE);
                networkBtn.setVisibility(View.VISIBLE);
                break;
        }

    }

    /**
     * 设置布局背景，其实就是边缘空隙的颜色，颜色取自顶部图片的主色调
     *
     * @param url
     */
    private void setMainContentBg(String url) {
        if (url == null) {
            mainContent.setBackgroundColor(ImageUtil.getImageColor(((BitmapDrawable) topImage.getBackground()).getBitmap()));
            return;
        }
        NetManageUtil.get(url)
                .enqueue(new InputStreamCallback() {
                    @Override
                    public void onSuccess(InputStream result, Headers headers) {
                        final Bitmap bitmap = BitmapFactory.decodeStream(result);
                        if(bitmap == null){
                            mainContent.setBackgroundColor(ImageUtil.getImageColor(((BitmapDrawable) topImage.getBackground()).getBitmap()));
                            return;
                        }
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                topImage.setBackground(new BitmapDrawable(getResources(), bitmap));
                                mainContent.setBackgroundColor(ImageUtil.getImageColor(bitmap));
                            }
                        });
                    }

                    @Override
                    public void onFailure(IOException e) {
                        mainContent.setBackgroundColor(ImageUtil.getImageColor(((BitmapDrawable) topImage.getBackground()).getBitmap()));
                    }
                });
    }


}