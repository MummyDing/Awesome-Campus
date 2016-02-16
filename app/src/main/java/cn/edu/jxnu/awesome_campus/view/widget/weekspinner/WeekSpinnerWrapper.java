package cn.edu.jxnu.awesome_campus.view.widget.weekspinner;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TimeUtil;

/**
 * Created by MummyDing on 16-2-5.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class WeekSpinnerWrapper{


    private Context mContext = InitApp.AppContext;

    private OnDayChangedListener listener;

    private int index = 0;


    public WeekSpinnerWrapper() {
    }

    public NiceSpinner build(NiceSpinner spinner){
        // init spinner
        spinner.setVisibility(View.VISIBLE);
        // init data
        List<String> daysOfWeek =new LinkedList<String>(
        Arrays.asList(mContext.getString(R.string.monday),mContext.getString(R.string.tuesday),
                mContext.getString(R.string.wednesday), mContext.getString(R.string.thusday),
                mContext.getString(R.string.friday),mContext.getString(R.string.saturday),
                mContext.getString(R.string.sunday)));

        spinner.attachDataSource(daysOfWeek);

        index = TimeUtil.getDayOfWeek() - 1;
        spinner.setSelectedIndex(index);
        listener.onDayChanged(index);
        if(listener != null) {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    listener.onDayChanged(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        return spinner;
    }

    public void setOnDayChangedListener(OnDayChangedListener listener) {
        this.listener = listener;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
