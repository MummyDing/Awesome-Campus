package cn.edu.jxnu.awesome_campus.ui.leisure;

import android.support.v4.widget.NestedScrollView;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.leisure.DailyDetailsBean;
import cn.edu.jxnu.awesome_campus.model.leisure.ScienceModel;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseDetailsActivity;

/**
 * Created by MummyDing on 16-2-13.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ScienceDetailsActivity extends BaseDetailsActivity {
    public static final String TAG = "DailyDetailsActivity";
    private ScienceModel model;
    @Override
    protected void onDataRefresh() {

    }

    @Override
    protected void onEventComing(EventModel eventModel) {

        switch (eventModel.getEventCode()){
            case EVENT.SEND_MODEL_DETAIL:
                model = (ScienceModel) eventModel.getData();
                initView();
                scrollView.setVisibility(View.VISIBLE);
                scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        topImage.setTranslationY(Math.max(-scrollY / 2, -DisplayUtil.dip2px(getBaseContext(), 170)));
                    }
                });
//                contentView.loadUrl(model.getUrl());
                contentView.loadDataWithBaseURL("file:///android_asset/", "<link rel=\"stylesheet\" type=\"text/css\" href=\"guokr.css\" />"+importStr(), "text/html", "utf-8", null);
                setMainContentBg(model.getImage_info().getUrl());
                break;
        }
    }
        private String importStr() {
                   InputStreamReader  inputReader = null;
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
    }

