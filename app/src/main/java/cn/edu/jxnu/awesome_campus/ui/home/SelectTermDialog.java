package cn.edu.jxnu.awesome_campus.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.edu.jxnu.awesome_campus.Config;
import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.home.TermSelectInfoBean;
import cn.edu.jxnu.awesome_campus.support.htmlparse.education.TermInfoParse;
import cn.edu.jxnu.awesome_campus.support.theme.ThemeConfig;
import cn.edu.jxnu.awesome_campus.view.widget.CommonSpinner;

/**
 * Created by MummyDing on 2017/5/25.
 */

public class SelectTermDialog extends Activity {

    private final static String TAG = "SelectTermDialog";
    private CommonSpinner mTermYearSelector;
    private CommonSpinner mTermOrderSelector;
    private HashMap<String, List<String>> mTermOrderMap = new HashMap<>();
    private Button mSelectTermButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(ThemeConfig.themeDialogStyle[Config.themeSelected]);
        super.onCreate(savedInstanceState);
        TCAgent.onPageStart(InitApp.AppContext, TAG);
        setContentView(R.layout.dialog_select_term);
        initView();
    }

    private void initView() {
        mTermYearSelector = (CommonSpinner) findViewById(R.id.term_year_spinner);
        mTermOrderSelector = (CommonSpinner) findViewById(R.id.term_order_spinner);
        mSelectTermButton = (Button) findViewById(R.id.select_term_button);
        final TermInfoParse parse = new TermInfoParse();
        TermSelectInfoBean termSelectInfo = parse.getTermSelectInfo();
        final List<TermSelectInfoBean.TermInfo> termInfoList = termSelectInfo.getTermList();
        if (termInfoList == null || termInfoList.isEmpty()) {
            // TODO: 2017/5/26 暂时不处理，默认，可以加一个错误页面
            return;
        }
        final List<String> termYearList = new ArrayList<>();
        Set<String> termStringSet = new HashSet<>();
        for (TermSelectInfoBean.TermInfo termInfo : termInfoList) {
            List<String> tmpList = new ArrayList<>();
            if (termStringSet.contains(termInfo.getTermYear())) {
                tmpList.addAll(mTermOrderMap.get(termInfo.getTermYear()));
                tmpList.add(termInfo.getTermOrder());
                mTermOrderMap.put(termInfo.getTermYear(), tmpList);
                continue;
            }
            termStringSet.add(termInfo.getTermYear());
            termYearList.add(termInfo.getTermYear());
            tmpList.add(termInfo.getTermOrder());
            mTermOrderMap.put(termInfo.getTermYear(), tmpList);
        }
        mTermYearSelector.setItems(termYearList);

        String year = termSelectInfo.getTermYearByIndex(termSelectInfo.getCurrentSelectIndex());
        for (int i = 0; i < termYearList.size(); i++) {
            if (termYearList.get(i).equals(year)) {
                mTermYearSelector.setSelectedIndex(i);
                mTermOrderSelector.setItems(mTermOrderMap.get(mTermYearSelector.getText()));
            }
        }

        mTermYearSelector.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                mTermOrderSelector.setItems(mTermOrderMap.get(mTermYearSelector.getText()));
            }
        });

        mSelectTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String termYear = (String) mTermYearSelector.getText();
                String termOrder = (String) mTermOrderSelector.getText();
                for (int i = 0; i < termInfoList.size(); i++) {
                    TermSelectInfoBean.TermInfo termInfo = termInfoList.get(i);
                    if (termInfo.getTermYear().equals(termYear) && termInfo.getTermOrder().equals(termOrder)) {
                        parse.updateCurrentTermIndex(i);
                    }
                    EventBus.getDefault().post(new EventModel<Void>(EVENT.COURSE_TABLE_REQUEST_REFRESH));
                    finish();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        TCAgent.onPageEnd(InitApp.AppContext, TAG);
        super.onDestroy();
    }

}
