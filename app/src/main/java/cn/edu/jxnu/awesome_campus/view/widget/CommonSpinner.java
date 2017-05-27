package cn.edu.jxnu.awesome_campus.view.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.jaredrummler.materialspinner.MaterialSpinner;


/**
 * Created by MummyDing on 2017/5/26.
 */

public class CommonSpinner extends MaterialSpinner {

    public CommonSpinner(Context context) {
        this(context, null);
    }

    public CommonSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
