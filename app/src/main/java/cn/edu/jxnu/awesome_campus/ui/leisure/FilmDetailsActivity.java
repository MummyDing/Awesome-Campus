package cn.edu.jxnu.awesome_campus.ui.leisure;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.squareup.okhttp.Headers;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.leisure.FilmModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.leisure.JianshuContentParse;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;
import cn.edu.jxnu.awesome_campus.ui.base.BaseDetailsActivity;

/**
 * Created by MummyDing on 16-2-17.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class FilmDetailsActivity extends BaseDetailsActivity {
    public static final String TAG = "FilmDetailsActivity";
    private FilmModel model = new FilmModel();
    @Override
    protected void onDataRefresh() {
        // 这里请求详情
        NetManageUtil.get(model.getUrl())
                .addTag(TAG)
                .enqueue(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Headers headers) {
                        JianshuContentParse myParse = new JianshuContentParse(result);
                        model.setDetail(myParse.getEndStr());
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                onEventMainThread(new EventModel<FilmModel>(EVENT.FILM_DETAILS_REFRESH_SUCCESS));
                            }
                        });
                    }

                    @Override
                    public void onFailure(String error) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                onEventMainThread(new EventModel<FilmModel>(EVENT.FILM_DETAILS_REFRESH_FAILURE));
                            }
                        });
                    }
                });
    }

    @Override
    public void initView() {
        super.initView();

        /*LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) contentLayout.getLayoutParams();
        layoutParams.setMargins(0,0,0,0);
        contentLayout.setLayoutParams(layoutParams);*/
    }

    @Override
    protected void onEventComing(EventModel eventModel) {
        switch (eventModel.getEventCode()){
            case EVENT.SEND_MODEL_DETAIL:
                model = (FilmModel) eventModel.getData();
                initView();
                break;
            case EVENT.FILM_DETAILS_REFRESH_SUCCESS:
                scrollView.setVisibility(View.VISIBLE);
                scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        topImage.setTranslationY(Math.max(-scrollY / 2, -DisplayUtil.dip2px(getBaseContext(), 170)));
                    }
                });
//                contentView.loadDataWithBaseURL("file:///android_asset/",  model.getDetail(), "text/html", "utf-8", null);
                contentView.loadUrl(model.getUrl());
                setMainContentBg(model.getTopPic());

                hideLoading();
                break;
            case EVENT.FILM_DETAILS_REFRESH_FAILURE:
                hideLoading();
                displayNetworkError();
                break;
        }
    }

    @Override
    protected String getShareInfo() {
        return "["+model.getTitle()+"]:"+model.getUrl()+" ( "+getString(R.string.text_share_from)+getString(R.string.app_name)+")";

    }
}
