package cn.edu.jxnu.awesome_campus.ui.home;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import com.squareup.okhttp.Headers;
import com.tendcloud.tenddata.TCAgent;


import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.database.DatabaseHelper;
import cn.edu.jxnu.awesome_campus.database.table.home.CampusNewsTable;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.home.CampusNewsModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.home.CampusNewsContentParse;
import cn.edu.jxnu.awesome_campus.support.config.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
import cn.edu.jxnu.awesome_campus.support.utils.html.GetNewsFirstPic;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;
import cn.edu.jxnu.awesome_campus.ui.base.BaseDetailsActivity;


/**
 * Created by MummyDing on 16-2-10.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */

public class CampusNewsDetailsActivity extends BaseDetailsActivity{

    public static final String TAG = "CampusNewsDetailsActivity";

    private CampusNewsModel model;
    private Handler handler = new Handler(Looper.getMainLooper());


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TCAgent.onPageStart(InitApp.AppContext, TAG);
    }

    @Override
    protected void onDataRefresh() {
        if (TextUtil.isNull(model.getNewsDetails())){
            NetManageUtil.get(Urlconfig.CampusNews_Base_URL + model.getNewsURL())
                    .addTag(TAG)
                    .enqueue(new StringCallback() {
                        @Override
                        public void onSuccess(String result, Headers headers) {
                            CampusNewsContentParse myParse = new CampusNewsContentParse(result);
                            model.setNewsPicURL(GetNewsFirstPic.getPicURL(myParse.getEndStr()));
                            model.setNewsDetails(myParse.getEndStr());
                            // 存入数据库
                            DatabaseHelper.exeSQL(CampusNewsTable.UPDATE_DETAILS,model.getNewsDetails(),model.getNewsTitle());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onEventMainThread(new EventModel<CampusNewsModel>(EVENT.CAMPUS_NEWS_DETAILS_REFRESH_SUCCESS));
                                }
                            });

                        }

                        @Override
                        public void onFailure(String error) {

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onEventMainThread(new EventModel<CampusNewsModel>(EVENT.CAMPUS_NEWS_DETAILS_REFRESH_FAILURE));
                                }
                            });

                        }
                    });
        }else{
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onEventMainThread(new EventModel<CampusNewsModel>(EVENT.CAMPUS_NEWS_DETAILS_REFRESH_SUCCESS));
                }
            });

        }
    }




    @Override
    protected void onEventComing(EventModel eventModel) {
        switch (eventModel.getEventCode()) {
            case EVENT.SEND_MODEL_DETAIL:
                model = (CampusNewsModel) eventModel.getData();
                initView();
                break;
            case EVENT.CAMPUS_NEWS_DETAILS_REFRESH_SUCCESS:
                scrollView.setVisibility(View.VISIBLE);
                scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        topImage.setTranslationY(Math.max(-scrollY / 2, -DisplayUtil.dip2px(getBaseContext(), 170)));
                    }
                });
                contentView.loadDataWithBaseURL("file:///android_asset/", "<link rel=\"stylesheet\" type=\"text/css\" href=\"CampusNews.css\" />" + model.getNewsDetails(), "text/html", "utf-8", null);
                setMainContentBg(model.getNewsPicURL());
                hideLoading();
                break;
            case EVENT.CAMPUS_NEWS_DETAILS_REFRESH_FAILURE:
                hideLoading();
                displayNetworkError();
                break;
        }
    }

    @Override
    protected String getShareInfo() {
        return "["+model.getNewsTitle()+"]:"+ Urlconfig.CampusNews_Base_URL+model.getNewsURL()+" ( "+getString(R.string.text_share_from)+" "+getString(R.string.app_name)+")";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(InitApp.AppContext, TAG);
    }
}