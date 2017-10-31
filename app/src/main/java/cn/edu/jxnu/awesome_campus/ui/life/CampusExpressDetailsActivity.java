package cn.edu.jxnu.awesome_campus.ui.life;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tendcloud.tenddata.TCAgent;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.life.CampusExpressModel;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseDetailsActivity;

public class CampusExpressDetailsActivity extends BaseDetailsActivity {

    public static final String TAG = "CampusExpressDetailsActivity";
    private CampusExpressModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TCAgent.onPageStart(InitApp.AppContext, TAG);

    }

    @Override
    protected void onDataRefresh() {

    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onEventComing(EventModel eventModel) {
        switch (eventModel.getEventCode()) {
            case EVENT.SEND_MODEL_DETAIL:
                model = (CampusExpressModel) eventModel.getData();
                isShowBigPic=true;//可显示大图
                initView();
                scrollView.setVisibility(View.VISIBLE);
                scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        topImage.setTranslationY(Math.max(-scrollY / 2, -DisplayUtil.dip2px(getBaseContext(), 170)));
                    }
                });
                contentView.loadDataWithBaseURL("file:///android_asset/", "<link rel=\"stylesheet\" type=\"text/css\" href=\"CampusNews.css\" />" + model.getBody(), "text/html", "utf-8", null);
                setMainContentBg(model.getTopImage());
                hideLoading();
                break;
        }
    }


    @Override
    protected String getShareInfo() {
        if (model == null) {
            return null;
        }
        return "[" + model.getExpTextName() + "]:\n" + "Location:" + model.getExpLoc()
                + "\nTel:" + model.getExpTel() + getString(R.string.text_share_from) + " ( " + getString(R.string.text_share_from) + " " + getString(R.string.app_name) + ")";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(InitApp.AppContext, TAG);
    }


}
