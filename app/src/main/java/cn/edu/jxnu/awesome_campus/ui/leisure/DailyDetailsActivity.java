package cn.edu.jxnu.awesome_campus.ui.leisure;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.NestedScrollView;
import android.view.View;


import com.squareup.okhttp.Headers;
import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.database.DatabaseHelper;
import cn.edu.jxnu.awesome_campus.database.table.leisure.DailyTable;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.leisure.DailyDetailsBean;
import cn.edu.jxnu.awesome_campus.model.leisure.DailyModel;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
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
    private DailyModel model;
    private static Handler handler = new Handler(Looper.getMainLooper());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TCAgent.onPageStart(InitApp.AppContext, TAG);
    }

    @Override
    protected void onDataRefresh() {

        if (TextUtil.isNull(model.getBody())) {
            NetManageUtil.post(model.getUrl())
                    .addTag(TAG)
                    .enqueue(new JsonEntityCallback<DailyDetailsBean>() {
                @Override
                public void onFailure(IOException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new EventModel<DailyDetailsBean>(EVENT.DAILY_DETAIL_FAILURE));
                        }
                    });
                }

                @Override
                public void onSuccess(final DailyDetailsBean entity, Headers headers) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (entity == null) {
                                EventBus.getDefault().post(new EventModel<DailyDetailsBean>(EVENT.DAILY_DETAIL_FAILURE));
                            } else {
                                model.setBody(entity.getBody());
                                model.setLargePic(entity.getImage());
                                //更新数据库
                                DatabaseHelper.exeSQL(DailyTable.UPDATE_DETAILS,entity.getBody(),entity.getImage(),model.getTitle());
                                EventBus.getDefault().post(new EventModel<DailyDetailsBean>(EVENT.DAILY_DETAIL_SUCCESS));
                            }
                        }
                    });
                }
            });
        }else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new EventModel<DailyDetailsBean>(EVENT.DAILY_DETAIL_SUCCESS));
                }
            });
        }

    }

    @Override
    protected void onEventComing(EventModel eventModel) {
        switch (eventModel.getEventCode()){
            case EVENT.SEND_MODEL_DETAIL:
                model = (DailyModel) eventModel.getData();
                initView();
                break;
            case EVENT.DAILY_DETAIL_SUCCESS:
                scrollView.setVisibility(View.VISIBLE);
                scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        topImage.setTranslationY(Math.max(-scrollY / 2, -DisplayUtil.dip2px(getBaseContext(), 170)));
                    }
                });
                contentView.loadDataWithBaseURL("file:///android_asset/", "<link rel=\"stylesheet\" type=\"text/css\" href=\"Daily.css\" />" + model.getBody(), "text/html", "utf-8", null);
                setMainContentBg(model.getLargePic());
                hideLoading();
                break;
            case EVENT.DAILY_DETAIL_FAILURE:
                hideLoading();
                displayNetworkError();
                break;
        }

    }

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
