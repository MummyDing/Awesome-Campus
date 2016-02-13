package cn.edu.jxnu.awesome_campus.ui.home;


import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import com.squareup.okhttp.Headers;



import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.home.CampusNewsModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.CampusNewsContentParse;
import cn.edu.jxnu.awesome_campus.support.urlconfig.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
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

    @Override
    protected void onDataRefresh() {
        NetManageUtil.get(Urlconfig.CampusNews_Base_URL + model.getNewsURL())
                .addTag(TAG)
                .enqueue(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Headers headers) {
                        CampusNewsContentParse myParse = new CampusNewsContentParse(result);
                        model.setNewsPicURL(GetNewsFirstPic.getPicURL(myParse.getEndStr()));
                        model.setNewsDetails(myParse.getEndStr());
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                onEventMainThread(new EventModel<CampusNewsModel>(EVENT.CAMPUS_NEWS_DETAILS_REFRESH_SUCCESS));
                            }
                        });
                    }

                    @Override
                    public void onFailure(String error) {
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
    protected void onEventComing(EventModel eventModel) {
        switch (eventModel.getEventCode()) {
            case EVENT.SEND_MODEL_DETAIL:
                if (eventModel.getEventCode() == EVENT.SEND_MODEL_DETAIL) {
                    model = (CampusNewsModel) eventModel.getData();
                    initView();
                }
                break;
            case EVENT.CAMPUS_NEWS_DETAILS_REFRESH_SUCCESS:
                scrollView.setVisibility(View.VISIBLE);
                scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        topImage.setTranslationY(Math.max(-scrollY / 2, -DisplayUtil.dip2px(getBaseContext(), 170)));
                    }
                });
                contentView.loadDataWithBaseURL("file:///android_asset/", "<link rel=\"stylesheet\" type=\"text/css\" href=\"MyCss.css\" />" + model.getNewsDetails(), "text/html", "utf-8", null);
                setMainContentBg(model.getNewsPicURL());
                hideLoading();
                break;
            case EVENT.CAMPUS_NEWS_DETAILS_REFRESH_FAILURE:
                hideLoading();
                displayNetworkError();
                break;
        }
    }




}