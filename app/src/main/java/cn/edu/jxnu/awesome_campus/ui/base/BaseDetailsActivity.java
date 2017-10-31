package cn.edu.jxnu.awesome_campus.ui.base;

import android.content.Context;
import android.content.Intent;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.ImageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
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

    private static final String TAG="BaseDetailsActivity";
    protected Toolbar toolbar;
    protected boolean isShowBigPic=false;
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
        return R.layout.activity_base_details;
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
            Log.d(TAG,"显示网络错误提示");
            networkBtn.setVisibility(View.VISIBLE);
        }
    }

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

        // 开启缓存
        contentView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        contentView.getSettings().setDomStorageEnabled(true);
        contentView.getSettings().setDatabaseEnabled(true);

        contentView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoading();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                displayNetworkError();
            }

//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//               displayNetworkError();
//            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                contentView.loadUrl(url);
                return false;
            }
        });

        Log.d(TAG,"大图显示状态"+isShowBigPic);
        if(isShowBigPic){
            setBigPicConfig();
        }
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

    protected void setBigPicConfig() {
        contentView.addJavascriptInterface(new JavascriptInterface(this),
                "imagelistener");
        contentView.setWebViewClient(new WebViewClient() {

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                // 监听器加载这是为了防止动态加载图片时新加载的图片无法预览
                addImageClickListener();
            }
        });
    }


    /**
     * 设置布局背景，其实就是边缘空隙的颜色，颜色取自顶部图片的主色调
     *
     * @param url
     */
    protected void setMainContentBg(String url) {
        if (TextUtil.isNull(url)) {
            setDefaultColor();
            return;
        }
        NetManageUtil.get(url)
                .enqueue(new InputStreamCallback() {
                    @Override
                    public void onSuccess(InputStream result, Headers headers) {
                        final Bitmap bitmap = BitmapFactory.decodeStream(result);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if(bitmap == null){
                                    setDefaultColor();
                                    return;
                                }
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, getShareInfo());
            startActivity(Intent.createChooser(sharingIntent, getString(R.string.hint_share_to)));
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    protected abstract String getShareInfo();
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
            contentView.loadUrl(imageloadJS);
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
