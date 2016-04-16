package cn.edu.jxnu.awesome_campus.ui.widget;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.util.Log;
import cn.edu.jxnu.awesome_campus.ui.base.BaseWidgetPrivider;

/**
 * Created by KevinWu on 16-3-5.
 */
public class CourseTable4x2Privider extends BaseWidgetPrivider {
    public static final String TAG = "CourseTable4x2Privider";


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    protected void init() {
        Log.d("初始化控件，类为","--"+this.getClass());
        super.reFreshTime=10000;//10秒刷新
        super.object=CourseTable4x2Service.class;
    }
}



