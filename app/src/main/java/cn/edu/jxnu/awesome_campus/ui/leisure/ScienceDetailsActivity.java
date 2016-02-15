package cn.edu.jxnu.awesome_campus.ui.leisure;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.NestedScrollView;
import android.view.View;

import com.squareup.okhttp.Headers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.leisure.ScienceModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.ScienceContentParse;
import cn.edu.jxnu.awesome_campus.support.urlconfig.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
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

    @Override
    protected void onDataRefresh() {
        NetManageUtil.get(model.getUrl())
                .addTag(TAG)
                .enqueue(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Headers headers) {
                        ScienceContentParse myParse = new ScienceContentParse(result);
                        model.setScienceDetails(myParse.getEndStr());
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                onEventMainThread(new EventModel<ScienceModel>(EVENT.SCIENCE_DETAILS_REFRESH_SUCCESS));
                            }
                        });
                    }

                    @Override
                    public void onFailure(String error) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                onEventMainThread(new EventModel<ScienceModel>(EVENT.SCIENCE_DETAILS_REFRESH_FAILURE));
                            }
                        });
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
        return "["+model.getTitle()+"]:"+model.getUrl()+"(share from "+getString(R.string.app_name)+")";
    }
}

