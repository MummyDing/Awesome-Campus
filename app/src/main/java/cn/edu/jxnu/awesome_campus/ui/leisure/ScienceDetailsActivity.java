package cn.edu.jxnu.awesome_campus.ui.leisure;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.squareup.okhttp.Headers;
import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.database.DatabaseHelper;
import cn.edu.jxnu.awesome_campus.database.table.leisure.ScienceTable;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.leisure.DailyDetailsBean;
import cn.edu.jxnu.awesome_campus.model.leisure.FilmModel;
import cn.edu.jxnu.awesome_campus.model.leisure.ScienceModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.leisure.ScienceContentParse;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;
import cn.edu.jxnu.awesome_campus.ui.base.BaseDetailsActivity;

/**
 * Created by MummyDing on 16-2-13.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ScienceDetailsActivity extends BaseDetailsActivity {
    public static final String TAG = "ScienceDetailsActivity";
    private ScienceModel model;

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TCAgent.onPageStart(InitApp.AppContext, TAG);
    }

    @Override
    protected void onDataRefresh() {

        if (TextUtil.isNull(model.getScienceDetails())) {
            NetManageUtil.get(model.getUrl())
                    .addTag(TAG)
                    .enqueue(new StringCallback() {
                        @Override
                        public void onSuccess(String result, Headers headers) {
                            ScienceContentParse myParse = new ScienceContentParse(result);
                            model.setScienceDetails(myParse.getEndStr());
                            DatabaseHelper.exeSQL(ScienceTable.UPDATE_DETAILS, model.getScienceDetails(), model.getTitle());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onEventMainThread(new EventModel<ScienceModel>(EVENT.SCIENCE_DETAILS_REFRESH_SUCCESS));
                                }
                            });
                        }

                        @Override
                        public void onFailure(String error) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onEventMainThread(new EventModel<ScienceModel>(EVENT.SCIENCE_DETAILS_REFRESH_FAILURE));
                                }
                            });
                        }
                    });
        }else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new EventModel<DailyDetailsBean>(EVENT.SCIENCE_DETAILS_REFRESH_SUCCESS));
                }
            });
        }
    }


    @Override
    public void initView() {
        super.initView();

        contentView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                onEventMainThread(new EventModel<ScienceModel>(EVENT.SCIENCE_DETAILS_REFRESH_FAILURE));
            }
        });
    }

    @Override
    protected void onEventComing(EventModel eventModel) {
        switch (eventModel.getEventCode()) {
            case EVENT.SEND_MODEL_DETAIL:
                model = (ScienceModel) eventModel.getData();
                initView();
                break;
            case EVENT.SCIENCE_DETAILS_REFRESH_SUCCESS:
                scrollView.setVisibility(View.VISIBLE);
                scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        topImage.setTranslationY(Math.max(-scrollY / 2, -DisplayUtil.dip2px(getBaseContext(), 170)));
                    }
                });
                contentView.loadDataWithBaseURL("file:///android_asset/", "<link rel=\"stylesheet\" type=\"text/css\" href=\"guokr.css\" />" + model.getScienceDetails(), "text/html", "utf-8", null);
                setMainContentBg(model.getImage_info().getUrl());
                hideLoading();
                break;
            case EVENT.SCIENCE_DETAILS_REFRESH_FAILURE:
                hideLoading();
                displayNetworkError();
                break;
        }
    }

   /* private String importStr() {
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
    }*/

    @Override
    protected String getShareInfo() {
        return "["+model.getTitle()+"]:"+model.getUrl()+" ( "+getString(R.string.text_share_from)+" "+getString(R.string.app_name)+")";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(InitApp.AppContext, TAG);
    }
}

