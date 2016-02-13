package cn.edu.jxnu.awesome_campus.ui.leisure;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.NestedScrollView;
import android.view.View;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import cn.edu.jxnu.awesome_campus.api.DailyApi;
import cn.edu.jxnu.awesome_campus.database.dao.leisure.DailyDetailsDAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.leisure.DailyDetailsBean;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;
import cn.edu.jxnu.awesome_campus.ui.base.BaseDetailsActivity;

/**
 * Created by MummyDing on 16-2-13.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class DailyDetailsActivity extends BaseDetailsActivity{

    public static final String TAG = "DailyDetailsActivity";
    private DailyDetailsBean model;
    private String url;
    private DailyDetailsDAO dao = new DailyDetailsDAO();
    @Override
    protected void onDataRefresh() {
        dao.loadFromNet();
    }

    @Override
    protected void onEventComing(EventModel eventModel) {

        switch (eventModel.getEventCode()){
            case EVENT.SEND_MODEL_DETAIL:
                url = DailyApi.daily_details_url+eventModel.getData();
                dao.setUrl(url);
                initView();
                break;
            case EVENT.DAILY_DETAIL_SUCCESS:
                model = (DailyDetailsBean) eventModel.getData();
                scrollView.setVisibility(View.VISIBLE);
                scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        topImage.setTranslationY(Math.max(-scrollY / 2, -DisplayUtil.dip2px(getBaseContext(), 170)));
                    }
                });
                contentView.loadDataWithBaseURL("file:///android_asset/", "<link rel=\"stylesheet\" type=\"text/css\" href=\"Daily.css\" />" + model.getBody(), "text/html", "utf-8", null);
                setMainContentBg(model.getImage());
                hideLoading();
                break;
            case EVENT.DAILY_DETAIL_FAILURE:
                hideLoading();
                displayNetworkError();
                break;
        }

    }
}
