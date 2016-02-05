package cn.edu.jxnu.awesome_campus.view.widget.weekspinner;

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

    private int defaultPadding;

    private int offSet = 60;
    private OnDayChangedListener listener;

    public WeekSpinnerWrapper(Context mContext) {
        this.mContext = mContext;
        defaultPadding = DisplayUtil.dip2px(mContext,30);
    }

    public Spinner build(){

        int spinnerWidth = DisplayUtil.getScreenWidth(mContext) - defaultPadding;
        // init spinner
        spinner = new Spinner(mContext);
        spinner.setDropDownVerticalOffset(DisplayUtil.dip2px(mContext,offSet));
        spinner.setDropDownHorizontalOffset(DisplayUtil.dip2px(mContext,offSet));
        spinner.setDropDownWidth(spinnerWidth - defaultPadding);
        spinner.setPadding(defaultPadding/2,defaultPadding,defaultPadding/2,defaultPadding/2);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(spinnerWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        spinner.setLayoutParams(lp);
        //spinner.setBackground(ContextCompat.getDrawable(mContext,R.drawable.spinnerbg));
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
