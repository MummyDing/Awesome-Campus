package cn.edu.jxnu.awesome_campus.view.widget.weekspinner;

import android.content.Context;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;

/**
 * Created by MummyDing on 16-2-5.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class WeekSpinnerWrapper{

    private Spinner spinner;

    private Context mContext;


    private OnDayChangedListener listener;

    public WeekSpinnerWrapper(Context mContext) {
        this.mContext = mContext;
    }

    public Spinner build(){
        // init spinner
        spinner = new Spinner(mContext);
        spinner.setDropDownVerticalOffset(DisplayUtil.dip2px(mContext,50));
        spinner.setDropDownWidth(DisplayUtil.dip2px(mContext,200));
        int pd = DisplayUtil.dip2px(mContext,50);
        spinner.setPadding(pd/2,pd/2,pd/2,pd);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        spinner.setLayoutParams(lp);

        // init adapter
        String [] daysOfWeek =
                {mContext.getString(R.string.monday),mContext.getString(R.string.tuesday),
                        mContext.getString(R.string.wednesday), mContext.getString(R.string.thusday),
                        mContext.getString(R.string.friday),mContext.getString(R.string.saturday),
                        mContext.getString(R.string.sunday)};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item,daysOfWeek);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

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
}
